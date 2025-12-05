package com.soon9086.basic1.boundedContext.member.controller;

import com.soon9086.basic1.base.rq.Rq;
import com.soon9086.basic1.base.rsData.RsData;
import com.soon9086.basic1.boundedContext.member.dto.MemberDTO;
import com.soon9086.basic1.boundedContext.member.entity.Member;
import com.soon9086.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@Controller
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;
    private final String kakaoClientId;
    private final String kakaoRedirectUri;

    // ✅ 생성자에서 @Value로 프로퍼티 주입 (최신 권장 방식)
    public MemberController(
            MemberService memberService,
            Rq rq,
            @Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoClientId,
            @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}") String kakaoRedirectUri
    ) {
        this.memberService = memberService;
        this.rq = rq;
        this.kakaoClientId = kakaoClientId;
        this.kakaoRedirectUri = kakaoRedirectUri;
    }

    // 회원가입 페이지
    @GetMapping("/member/join")
    public String showRegisterPage(HttpSession session) {
        return "member/join";  // --> /WEB-INF/views/member/join.jsp
    }

    // 회원가입 처리
    @PostMapping("/member/join")
    public String doRegister(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String confirm,
                             HttpSession session,
                             Model model) {

        // 비밀번호 확인 체크
        if (!password.equals(confirm)) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "member/join";   // redirect 사용하면 데이터가 사라지므로 주의!
        }

        System.out.printf("회원가입 성공: %s / %s%n", username, email);
        // 세션에 임시 저장
        session.setAttribute("tmp_username", username);
        session.setAttribute("tmp_email", email);
        session.setAttribute("tmp_password", password);

        return "redirect:/auth/phone";
    }

    // 로그인페이지
    @GetMapping("/member/login")
    public String login(Model model) {
        log.info("Member login get......");
        model.addAttribute("kakaoClientId", kakaoClientId);
        model.addAttribute("kakaoRedirectUri", kakaoRedirectUri);
        return "member/login";  // → /WEB-INF/views/member/login.jsp
    }

    // 로그인 처리
    @PostMapping("/member/loginProcess")
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
            MemberDTO member = (MemberDTO) rsData.getData();
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
        // DB에서 회원 정보 가져오기
        MemberDTO member = memberService.findById(loginedMemberId);
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
