package com.soon9086.basic1.config;

import com.soon9086.basic1.interceptor.PhoneAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "downloads" 폴더를 정적 리소스로 매핑
        registry.addResourceHandler("/downloads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/downloads/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PhoneAuthInterceptor())
                .addPathPatterns(
                        "/member/join/**",      // 회원가입 페이지 보호
                        "/auth/phone/**"   // 인증 프로세스 보호
                )
                .excludePathPatterns(
                        "/auth/phone",          // 첫 페이지
                        "/auth/phone/send",     // 인증 요청
                        "/auth/phone/code",     // 인증 코드 입력
                        "/auth/phone/resend",   // 재전송
                        "/auth/phone/verify"    // 인증 검증
                );
    }
}
