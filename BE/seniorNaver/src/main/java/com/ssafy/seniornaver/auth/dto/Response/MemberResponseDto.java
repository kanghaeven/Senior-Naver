package com.ssafy.seniornaver.auth.dto.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Getter
@NoArgsConstructor
@Slf4j
public class MemberResponseDto {


    private String memberId;

    private String mobile;

    private String name;

    private String nickname;

    private String email;

    private String profileUrl;

    private String region;

    private List<String> keywords;

    // 생성자
    @Builder
    public MemberResponseDto(String memberId, String mobile, String name, String nickname, String email, String profileUrl, String region, List<String> keywords) {
        this.memberId = memberId;
        this.mobile = mobile;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
        this.region = region;
        this.keywords = keywords;
    }
}
