package com.ssafy.seniornaver.location.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeywordSearchRequestDto {
    private int page;
    private String keyword;
}
