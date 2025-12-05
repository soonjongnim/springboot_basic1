package com.soon9086.basic1.boundedContext.auth.service;

import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthRequestDto;
import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthSendResponseDto;
import com.soon9086.basic1.boundedContext.auth.dto.PhoneAuthVerifyRequestDto;

public interface PhoneAuthService {
    PhoneAuthSendResponseDto sendAuthCode(PhoneAuthRequestDto dto);
    boolean verifyAuthCode(PhoneAuthVerifyRequestDto dto);
    PhoneAuthSendResponseDto resendAuthCode(String oldRequestId);
}
