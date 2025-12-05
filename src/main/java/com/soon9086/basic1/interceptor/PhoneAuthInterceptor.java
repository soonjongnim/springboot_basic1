package com.soon9086.basic1.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class PhoneAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean isAuthSuccess = false;

        if (session != null) {
            Object flag = session.getAttribute("PHONE_AUTH_SUCCESS");
            isAuthSuccess = (flag != null && flag.equals(true));
        }

        String uri = request.getRequestURI();
        System.out.println("isAuthSuccess:" + isAuthSuccess);
        // 회원가입 접근 시 인증 안되면 인증 페이지로 이동
        if (uri.startsWith("/member/join")) {
            if (!isAuthSuccess) {
                response.sendRedirect("/auth/phone");
                return false;
            }
        }

        // 이미 인증된 사용자가 인증 페이지 접근하는 경우 → 바로 회원가입으로 이동
        if (uri.startsWith("/auth/phone")) {
            if (isAuthSuccess) {
                response.sendRedirect("/member/join");
                return false;
            }
        }

        return true;
    }
}
