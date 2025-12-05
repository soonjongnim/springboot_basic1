package com.soon9086.basic1.boundedContext.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDTO {
    private Long id;
    private String providerId; // 카카오,네이버,구글등등.. 로그인 ID
    private String username;
    private String email;
    private String password;
    private String phone;
    private String provider = "LOCAL"; // LOCAL: 일반(기본), 카카오, 네이버, 구글 로그인 타입
    private LocalDateTime createdAt;

    public MemberDTO(String providerId, String nickname, String email, String provider) {
        this.providerId = providerId;
        this.username = nickname;
        this.email = email;
        this.provider = provider;
    }
}
