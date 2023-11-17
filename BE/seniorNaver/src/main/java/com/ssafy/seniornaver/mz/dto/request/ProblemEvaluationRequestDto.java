package com.ssafy.seniornaver.mz.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemEvaluationRequestDto {
    private long problemId;
    private String title;
    private int answer;
    private int choice;
}
