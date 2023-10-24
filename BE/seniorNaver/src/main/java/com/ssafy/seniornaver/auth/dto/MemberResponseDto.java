package com.ssafy.seniornaver.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Getter
@NoArgsConstructor
@Slf4j
public class MemberResponseDto {

    // pk 값을 id로 받음. 유저 로그인 id를 고유값으로 만들기 위함.
    private String memberId;

    // 유저 닉네임
    private String nickname;


    // 생성자
    @Builder
    public MemberResponseDto(String memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
