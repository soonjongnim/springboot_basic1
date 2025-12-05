package com.soon9086.basic1.boundedContext.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneAuthVerifyRequestDto {
    private String requestId;
    private String code;
}
