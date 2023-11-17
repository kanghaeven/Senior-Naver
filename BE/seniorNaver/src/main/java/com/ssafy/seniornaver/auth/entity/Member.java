package com.ssafy.seniornaver.auth.entity;

import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import com.ssafy.seniornaver.auth.entity.enumType.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String memberId;

    @Column
    private String mobile;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column
    private String region;

    @Column
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Column(length = 300)
    private String refreshToken;

    @Column
    private Date tokenExpirationTime;

    @Column
    private Long vocaId;

    @Builder
    public Member(Long id, String memberId, String mobile, String name, String password, String nickname, String email,
                  String region, String profileUrl, Role role, AuthProvider authProvider, String refreshToken, Date tokenExpirationTime
                  ) {
        this.id = id;
        this.memberId = memberId;
        this.mobile = mobile;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.region = region;
        this.profileUrl =profileUrl;
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

    public void updateRegionAndNickname(String region,String nickname){
        this.region = region;
        this.nickname = nickname;
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
    public void updateRegion(String region){
        this.region = region;
    }
    public void createVocaId(Long vocaId) {
        this.vocaId = vocaId;
    }

}
