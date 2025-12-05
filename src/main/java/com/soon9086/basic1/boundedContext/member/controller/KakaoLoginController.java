package com.soon9086.basic1.boundedContext.member.controller;

import com.soon9086.basic1.boundedContext.member.dto.MemberDTO;
import com.soon9086.basic1.boundedContext.member.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/kakao")
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public String kakaoCallback(@RequestParam String code, HttpSession session) {

        // 1) 토큰 발급
        String accessToken = kakaoService.getAccessToken(code);

        // 2) 사용자 정보 조회
        MemberDTO userInfo = kakaoService.getUserInfo(accessToken);

        // 3) 로그인 처리 (DB 등록 or 기존 회원 조회)
        MemberDTO member = kakaoService.processKakaoLogin(userInfo);

        // 4) 세션 저장
        session.setAttribute("loginedMemberId", member.getId());

        return "<script>alert('카카오 로그인 성공!'); location.href='/' </script>";
    }
}

