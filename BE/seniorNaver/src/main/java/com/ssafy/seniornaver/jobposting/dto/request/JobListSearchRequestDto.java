package com.ssafy.seniornaver.jobposting.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobListSearchRequestDto {
    private int pageNum;
    private String keyword;
    private String workPlcNm;
}