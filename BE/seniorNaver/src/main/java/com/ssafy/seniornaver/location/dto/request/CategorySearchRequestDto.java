package com.ssafy.seniornaver.location.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategorySearchRequestDto {
    private int page;
    private String category;
    private String x;
    private String y;
}
