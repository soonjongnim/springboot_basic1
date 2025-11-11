package com.soon9086.basic1.boundedContext.member.controller;

import com.soon9086.basic1.base.rq.Rq;
import com.soon9086.basic1.base.rsData.RsData;
import com.soon9086.basic1.boundedContext.member.entity.Member;
import com.soon9086.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController() {
        memberService = new MemberService();
    }

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password, HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp);
        if(username.trim().isEmpty()) {
            return RsData.of("F-1", "아이디를 입력해주세요.");
        } else if(password.trim().isEmpty()) {
            return RsData.of("F-2", "비밀번호를 입력해주세요.");
        }

        RsData rsData = memberService.tryLogin(username, password);
        if(rsData.isSuccess()) {
            // 쿠키
            Member member = (Member) rsData.getData();
            rq.setCookie("loginedMemberId", member.getId());
        }
        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp);
        boolean cookieRemoved = rq.removeCookie("loginedMemberId");
        if(!cookieRemoved) {
            return RsData.of("F-1", "이미 로그아웃 상태입니다.");
        }

        return RsData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp);
        long loginedMemberId = rq.getCookieAsLong("loginedMemberId", 0);   // 0은 로그인이 안되어 있다는 의미
        boolean isLogined = loginedMemberId > 0;
        if(!isLogined) {
            return RsData.of("F-1", "로그인 후 이용해주세요.");
        }

        Member member = memberService.findById(loginedMemberId);
        return RsData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
    }
}
