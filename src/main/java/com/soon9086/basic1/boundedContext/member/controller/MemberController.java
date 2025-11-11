package com.soon9086.basic1.boundedContext.member.controller;

import com.soon9086.basic1.base.rsData.RsData;
import com.soon9086.basic1.boundedContext.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController() {
        memberService = new MemberService();
    }

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password) {
        if(username.trim().isEmpty()) {
            return RsData.of("F-1", "아이디를 입력해주세요.");
        } else if(password.trim().isEmpty()) {
            return RsData.of("F-2", "비밀번호를 입력해주세요.");
        }
        return memberService.tryLogin(username, password);
    }
}
