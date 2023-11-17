package com.ssafy.seniornaver.auth.dto.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class LogInResponseDto {
    private String memberId;
    private String nickname;
    private String email;
    private String mobile;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpirationTime;

    @Builder
    public LogInResponseDto(String memberId, String nickname, String email, String mobile, String accessToken, String refreshToken,
        Date refreshTokenExpirationTime) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.mobile = mobile;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}
