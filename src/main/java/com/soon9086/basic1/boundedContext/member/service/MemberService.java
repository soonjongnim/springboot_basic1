package com.soon9086.basic1.boundedContext.member.service;

import com.soon9086.basic1.base.rsData.RsData;
import com.soon9086.basic1.boundedContext.member.dto.MemberDTO;
import com.soon9086.basic1.boundedContext.member.entity.Member;
import com.soon9086.basic1.boundedContext.member.mapper.MemberMapper;
import com.soon9086.basic1.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

    public MemberDTO findById(Long id) {
        return memberMapper.findById(id);
    }

    // 이름 및 비번 확인
    public RsData tryLogin(String username, String password) {
        MemberDTO member = memberMapper.findByUsername(username);

        if(member == null) {
            return RsData.of("F-3", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        } else if(!member.getPassword().equals(password)) {
            return RsData.of("F-4", "비밀번호가 일치하지 않습니다.");
        }

        return RsData.of("S-1", "%s님 환영합니다.".formatted(username), member);
    }

    // 아이디 중복 체크
    public boolean isUsernameExists(String username) {
        return memberMapper.findByUsername(username) != null;
    }

    // 이메일 중복 체크
    public boolean isEmailExists(String email) {
        return memberMapper.findByEmail(email) != null;
    }

    // 회원가입 실행
    public boolean signup(MemberDTO dto) {
        // 이미 존재하는 아이디/이메일 체크
        if (isUsernameExists(dto.getUsername())) {
            return false;
        }
        if (isEmailExists(dto.getEmail())) {
            return false;
        }
        // DB 저장 로직 (예시)
        System.out.println("회원가입 정보 저장: " + dto);

        // 비밀번호 암호화(Optional)
        // dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        return memberMapper.insertMember(dto) > 0;
    }
}
