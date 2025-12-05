package com.soon9086.basic1.boundedContext.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhoneAuthSendResponseDto {
    private String resultCode;
    private String requestId;
    private String message;
}
