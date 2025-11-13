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
import org.springframework.web.bind.annotation.RequestParam;
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

    // 회원가입 페이지
    @GetMapping("/member/register")
    public String showRegisterPage() {
        return "member/register";  // --> /WEB-INF/views/member/register.jsp
    }

    // 회원가입 처리
    @PostMapping("/member/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String confirm) {

        if (!password.equals(confirm)) {
            System.out.println("비밀번호 불일치!");
            return "redirect:/member/register?error=password";
        }

        System.out.printf("회원가입 성공: %s / %s%n", username, email);
        return "redirect:/member/login";
    }

    // 로그인페이지
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

    // 로그인 처리
    @PostMapping("/member/login")
    public String login(String username, String password) {
        if(username.trim().isEmpty()) {
            // 로그인 페이지로 돌아가면서 에러 메시지 전달 가능
            return "redirect:/member/login?error=username";
        } else if(password.trim().isEmpty()) {
            return "redirect:/member/login?error=password";
        }

        RsData rsData = memberService.tryLogin(username, password);
        if(rsData.isSuccess()) {
            // 쿠키
            Member member = (Member) rsData.getData();
            rq.setSession("loginedMemberId", member.getId());
            // 로그인 성공 시 메인 페이지로 이동
            return "redirect:/";
        } else {
            // 로그인 실패 시 로그인 페이지로 돌아가기
            return "redirect:/member/login?error=fail";
        }
    }

    // 로그아웃 처리
    @PostMapping("/member/logout")
    public String logout() {
        boolean cookieRemoved = rq.removeSession("loginedMemberId");
        return "redirect:/";
    }

    // 회원정보페이지
    @GetMapping("/member/me")
    public String showMe(Model model) {
        long loginedMemberId = rq.getLoginedMember();
        System.out.println("loginedMemberId: " + loginedMemberId);
        Member member = memberService.findById(loginedMemberId);
        model.addAttribute("member", member);
        return "member/me";
    }

    // 세션 정보확인
    @GetMapping("/member/session")
    @ResponseBody
    public String showSession() {
        return rq.getSessionDebugInfo().replaceAll("\n", "<br>");
    }
}
