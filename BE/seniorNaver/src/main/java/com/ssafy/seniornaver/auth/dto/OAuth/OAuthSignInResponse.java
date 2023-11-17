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
    private String name;
    private String email;
    private String mobile;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpirationTime;

    @Builder
    public OAuthSignInResponse(
            AuthProvider authProvider
            ,String memberId
            ,String name
            ,String email
            ,String mobile
            ,String accessToken
            ,String refreshToken
            ,Date refreshTokenExpirationTime
    ){
        this.authProvider = authProvider;
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
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
                .name(name)
                .email(email)
                .mobile(mobile)
                .password(password)
                .profileUrl("https://d33nz7652hemr5.cloudfront.net/Profile/user-basic-profile.png")
                .role(Role.USER)
                .refreshToken(refreshToken)
                .tokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }

}
