package com.ssafy.seniornaver.mz.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RandomProblemResponseDto {
    private List<Long> problemList;

    @Builder
    public RandomProblemResponseDto(List<Long> problemList) {
        this.problemList = problemList;
    }
}
