package com.ssafy.seniornaver.auth.dto.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class KeywordRequestDto {
    private List<String> keywords;

    @Builder
    public KeywordRequestDto(List<String> keywords){
        this.keywords = keywords;
    }
}
