package com.ssafy.seniornaver.auth.entity;

import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import com.ssafy.seniornaver.auth.entity.enumType.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Member{
    @Id
    private String memberId;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Column(length = 300)
    private String refreshToken;

    private Date tokenExpirationTime;


    @Builder
    public Member(String memberId, String mobile, String name, String password, String nickname, String email,
                  String region, Role role, AuthProvider authProvider, String refreshToken, Date tokenExpirationTime
                  ) {
        this.memberId = memberId;
        this.mobile = mobile;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.region = region;
        this.role = role;
        this.authProvider = authProvider;
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = tokenExpirationTime;
    }

    /*
    ** 엔티티 관련 비즈니스 로직
     */
    public void passwordEncode(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String refreshToken, Date refreshTokenExpirationTime) {
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = refreshTokenExpirationTime;
    }

    public void expireRefreshToken(Date now) {
        this.tokenExpirationTime = now;
    }

}
