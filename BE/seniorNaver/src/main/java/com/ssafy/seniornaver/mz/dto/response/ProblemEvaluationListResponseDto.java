package com.ssafy.seniornaver.mz.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProblemEvaluationListResponseDto {

    private int page;
    private int totalPage;
    private List<Item> items;

    @Getter
    @Builder
    public static class Item {
        private long problemId;
        private String title;
        private int choice;
        private boolean answer;
    }

    @Builder
    public ProblemEvaluationListResponseDto(int page, int totalPage, int choice, List<Item> items) {
        this.page = page;
        this.totalPage = totalPage;
        this.items = items;
    }
}
