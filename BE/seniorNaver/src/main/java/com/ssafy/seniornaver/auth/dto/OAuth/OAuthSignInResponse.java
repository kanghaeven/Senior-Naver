package com.ssafy.seniornaver.auth.dto.OAuth;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import com.ssafy.seniornaver.auth.entity.enumType.Role;
import com.ssafy.seniornaver.util.PasswordUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class OAuthSignInResponse {
    private AuthProvider authProvider;

    private String memberId;
    private String mobile;
    private String nickname;
    private String email;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpirationTime;

    @Builder
    public OAuthSignInResponse(
            AuthProvider authProvider
            ,String memberId
            ,String mobile
            ,String nickname
            ,String email
            ,String accessToken
            ,String refreshToken
            ,Date refreshTokenExpirationTime
    ){
        this.authProvider = authProvider;
        this.memberId = memberId;
        this.mobile = mobile;
        this.nickname = nickname;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    public Member toEntity() {
        // OAuth는 따로 password 정보가 없기 때문에 임의로 생성
        String password = PasswordUtil.generateRandomPassword();
        return Member.builder()
                .memberId(memberId)
                .authProvider(authProvider)
                .nickname(nickname)
                .password(password)
                .email(email)
                .mobile(mobile)
                .role(Role.USER)
                .refreshToken(refreshToken)
                .tokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }

}
