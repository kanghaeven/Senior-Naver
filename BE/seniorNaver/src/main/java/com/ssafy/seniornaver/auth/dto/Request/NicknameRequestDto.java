package com.ssafy.seniornaver.auth.dto.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NicknameRequestDto {
    private String nickname;

    @Builder
    public NicknameRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
