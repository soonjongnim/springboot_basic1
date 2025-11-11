package com.soon9086.basic1.boundedContext.member.service;

import com.soon9086.basic1.base.rsData.RsData;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MemberService {
    public RsData tryLogin(String username, String password) {
        if(!username.equals("user1")) {
            return RsData.of("F-1", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        } else if(!password.equals("123")) {
            return RsData.of("F-2", "비밀번호가 일치하지 않습니다.");
        }

        return RsData.of("S-1", "%s님 환영합니다.\n".formatted(username));
    }
}
