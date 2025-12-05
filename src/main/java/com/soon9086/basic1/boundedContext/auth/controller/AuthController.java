package com.soon9086.basic1.boundedContext.auth.controller;

import com.soon9086.basic1.base.rq.Rq;
import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthRequestDto;
import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthSendResponseDto;
import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthVerifyRequestDto;
import com.soon9086.basic1.boundedContext.auth.service.PhoneAuthService;
import com.soon9086.basic1.boundedContext.member.dto.MemberDTO;
import com.soon9086.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final PhoneAuthService phoneAuthService;
    private final MemberService memberService;

    // 1. 휴대폰 정보 입력 화면
    @GetMapping("/phone")
    public String phoneAuthPage() {

        return "/auth/phoneAuth"; // phoneAuth.jsp
    }

    // 2단계: 인증번호 전송
    @PostMapping("/phone/send")
    public String sendAuthCode(PhoneAuthRequestDto dto, HttpSession session) {

        PhoneAuthSendResponseDto response = phoneAuthService.sendAuthCode(dto);

        // PASS API에서 받은 requestId 저장
        session.setAttribute("PHONE_AUTH_REQUEST_ID", response.getRequestId());

        // 다음 단계 화면으로 이동
        return "redirect:/auth/phone/code";
    }

    // 3단계: 인증번호 입력 화면 이동
    @GetMapping("/phone/code")
    public String authCodePage(HttpSession session) {
        Object requestId = session.getAttribute("PHONE_AUTH_REQUEST_ID");

        // 세션 없으면 처음 화면으로 이동
        if (requestId == null) {
            return "redirect:/auth/phone";
        }
        return "/auth/phoneAuthCode";   // phoneAuthCode.jsp
    }

    // 4단계: 인증번호 검증
    @PostMapping("/phone/verify")
    public String verifyCode(PhoneAuthVerifyRequestDto dto, HttpSession session) {

        boolean verified = phoneAuthService.verifyAuthCode(dto);

        if (!verified) {
            return "redirect:/auth/phone/code?error=fail";
        }
        // 인증 성공
        session.setAttribute("PHONE_AUTH_SUCCESS", true);

        // 🔥 1) 세션에서 회원가입 정보 꺼내기
        String username = (String) session.getAttribute("tmp_username");
        String email = (String) session.getAttribute("tmp_email");
        String password = (String) session.getAttribute("tmp_password");

        if (username == null || email == null || password == null) {
            return "redirect:/member/join?error=sessionExpired";
        }

        // 🔥 2) DTO에 담아서 서비스로 전달
        MemberDTO member = new MemberDTO();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(password);

        // 🔥 3) DB 저장 (회원가입 처리)
        boolean ok = memberService.signup(member);

        if (ok) {
            // 성공 후 임시 세션 삭제
            session.removeAttribute("tmp_username");
            session.removeAttribute("tmp_email");
            session.removeAttribute("tmp_password");
            session.removeAttribute("PHONE_AUTH_SUCCESS");
            session.removeAttribute("PHONE_AUTH_REQUEST_ID");
        }

        // 🔥 가입 완료 후 로그인 페이지로 이동 + 팝업 띄우기
        return "redirect:/member/login?signup=success";
    }

    @PostMapping("/phone/resend")
    @ResponseBody
    public PhoneAuthSendResponseDto resendAuthCode(HttpSession session) {
        Object requestIdObj = session.getAttribute("PHONE_AUTH_REQUEST_ID");
        if (requestIdObj == null) {
            return new PhoneAuthSendResponseDto("9999", "", "휴대폰 입력 화면으로 돌아가세요.");
        }
        String oldRequestId = (String) requestIdObj;
        PhoneAuthSendResponseDto response = phoneAuthService.resendAuthCode(oldRequestId);
        session.setAttribute("PHONE_AUTH_REQUEST_ID", response.getRequestId());
        return response;
    }

}