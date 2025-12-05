package com.soon9086.basic1.boundedContext.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneAuthRequestDto {
    private String name;
    private String birth;
    private String gender;
    private String carrier;
    private String phone;
}
