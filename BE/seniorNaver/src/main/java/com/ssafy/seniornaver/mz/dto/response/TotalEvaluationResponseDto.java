package com.ssafy.seniornaver.mz.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TotalEvaluationResponseDto {
    private List<Problem> problemList;

    @Getter
    @Builder
    public static class Problem {
        private Long id;
        private String title;
        private int choice;
        private boolean answer;
    }

    @Builder
    public TotalEvaluationResponseDto(List<Problem> problemList) {
        this.problemList = problemList;
    }
}
