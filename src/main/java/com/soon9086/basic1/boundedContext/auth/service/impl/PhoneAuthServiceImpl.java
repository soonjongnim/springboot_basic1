package com.soon9086.basic1.boundedContext.auth.service.impl;

import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthRequestDto;
import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthSendResponseDto;
import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthVerifyRequestDto;
import com.soon9086.basic1.boundedContext.auth.service.PhoneAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PhoneAuthServiceImpl implements PhoneAuthService {

    @Override
    public PhoneAuthSendResponseDto sendAuthCode(PhoneAuthRequestDto dto) {

        // 실제로는 PASS / 다날 인증 API 호출하는 자리
        log.info("휴대폰 인증 요청: {}, {}", dto.getName(), dto.getPhone());

        // API 응답 가정 (mock)
        String requestId = UUID.randomUUID().toString();

        return new PhoneAuthSendResponseDto(
                "0000",
                requestId,
                "인증번호가 전송되었습니다."
        );
    }

    @Override
    public boolean verifyAuthCode(PhoneAuthVerifyRequestDto dto) {

        // PASS 또는 다날 API에 인증번호 검증 요청해야 함
        log.info("인증번호 검증 요청: requestId={}, code={}",
                dto.getRequestId(), dto.getCode());

        // 테스트용: 인증번호 123456만 성공
        if ("123456".equals(dto.getCode())) {
            return true;
        }

        return false;
    }

    @Override
    public PhoneAuthSendResponseDto resendAuthCode(String oldRequestId) {

        // 실제 API 호출 시에는 oldRequestId 기반으로 재전송 요청
        log.info("인증번호 재전송 요청: oldRequestId={}", oldRequestId);

        // mock response 생성
        String newRequestId = UUID.randomUUID().toString();

        return new PhoneAuthSendResponseDto(
                "0000",
                newRequestId,
                "인증번호가 재전송되었습니다."
        );
    }

}
