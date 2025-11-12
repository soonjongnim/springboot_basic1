package com.soon9086.basic1.boundedContext.member.controller;

import com.soon9086.basic1.base.rq.Rq;
import com.soon9086.basic1.base.rsData.RsData;
import com.soon9086.basic1.boundedContext.member.entity.Member;
import com.soon9086.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@AllArgsConstructor // 자동 생성자 주입
@Controller
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    // 생성자 주입
    /*
    public MemberController() {
        memberService = new MemberService();
    }
    */

    @GetMapping("/member/login")
    public String login() {
//        if(rq.isLogined()) {
//            return """
//                    <script>
//                        alert("이미 로그인 되어있습니다.");
//                    </script>
//                    """;
//        }

        return "member/login";  // → /WEB-INF/views/member/login.jsp
    }

    @PostMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password) {
        if(username.trim().isEmpty()) {
            return RsData.of("F-1", "아이디를 입력해주세요.");
        } else if(password.trim().isEmpty()) {
            return RsData.of("F-2", "비밀번호를 입력해주세요.");
        }

        RsData rsData = memberService.tryLogin(username, password);
        if(rsData.isSuccess()) {
            // 쿠키
            Member member = (Member) rsData.getData();
            rq.setSession("loginedMemberId", member.getId());
        }
        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout() {
        boolean cookieRemoved = rq.removeSession("loginedMemberId");
        if(!cookieRemoved) {
            return RsData.of("F-1", "이미 로그아웃 상태입니다.");
        }

        return RsData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    public String showMe(Model model) {
        long loginedMemberId = rq.getLoginedMember();
        Member member = memberService.findById(loginedMemberId);
        model.addAttribute("member", member);
        return "member/me";
    }

    @GetMapping("/member/session")
    @ResponseBody
    public String showSession() {
        return rq.getSessionDebugInfo().replaceAll("\n", "<br>");
    }
}
