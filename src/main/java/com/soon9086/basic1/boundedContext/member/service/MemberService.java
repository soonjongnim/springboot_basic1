package com.soon9086.basic1.boundedContext.member.service;

import com.soon9086.basic1.base.rsData.RsData;
import com.soon9086.basic1.boundedContext.member.entity.Member;
import com.soon9086.basic1.boundedContext.member.repository.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
// @Component  // 컴포넌트가 붙은 클래스는 IOC 컨테이너에 의해 생성소멸이 관리된다.
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService() {
        memberRepository = new MemberRepository();
    }
    public RsData tryLogin(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if(member == null) {
            return RsData.of("F-3", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        } else if(!member.getPassword().equals(password)) {
            return RsData.of("F-4", "비밀번호가 일치하지 않습니다.");
        }

        return RsData.of("S-1", "%s님 환영합니다.".formatted(username), member);
    }

    public Member findByUsername(String user1) {
        return memberRepository.findByUsername(user1);
    }

    public Member findById(long id) {
        return memberRepository.fineById(id);
    }
}
