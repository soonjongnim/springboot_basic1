package com.soon9086.basic1.boundedContext.member.service;

import com.soon9086.basic1.boundedContext.member.dto.MemberDTO;
import com.soon9086.basic1.boundedContext.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class KakaoService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    private final MemberMapper memberMapper;

    // 1. Access Token 요청
    public String getAccessToken(String code) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        if (!clientSecret.isEmpty()) {
            params.add("client_secret", clientSecret);
        }

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        JSONObject json = new JSONObject(response.getBody());
        return json.getString("access_token");
    }

    // 2. 사용자 정보 가져오기
    public MemberDTO getUserInfo(String accessToken) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<HttpHeaders> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JSONObject json = new JSONObject(response.getBody());
        String providerId = String.valueOf(json.get("id"));
        String nickname = json.getJSONObject("properties").getString("nickname");
        String email = null;

        if (json.getJSONObject("kakao_account").has("email")) {
            email = json.getJSONObject("kakao_account").getString("email");
        }

        return new MemberDTO(providerId, nickname, email, "KAKAO");
    }

    // 3. DB 저장 + 로그인 처리
    public MemberDTO processKakaoLogin(MemberDTO userInfo) {
        // 1) provider_id 로 기존 회원 검색
        MemberDTO member = memberMapper.findByProviderId(userInfo.getProviderId());

        // 2) 기존 회원이 있다면 → 로그인 처리
        if (member != null) {
            return member;  // 그대로 로그인
        }

        // 3) 없다면 신규 생성
        member = MemberDTO.builder()
                .providerId(userInfo.getProviderId())
                .username(userInfo.getUsername())
                .email(userInfo.getEmail())
                .provider(userInfo.getProvider())
                .password(UUID.randomUUID().toString()) // 임시 비밀번호
                .build();

        memberMapper.insertMember(member);

        return member;
    }
}

