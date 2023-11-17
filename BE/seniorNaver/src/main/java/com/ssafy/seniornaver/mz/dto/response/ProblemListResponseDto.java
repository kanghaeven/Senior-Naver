package com.ssafy.seniornaver.mz.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemListResponseDto {

    private Long problemId;
    private String title;
    private String problemExplanation;
    private int useYear;
    private boolean save;
    private boolean complete;

    @Builder
    public ProblemListResponseDto(Long problemId, String title, String problemExplanation,
                                  int useYear, boolean save, boolean complete) {
        this.problemId = problemId;
        this.title = title;
        this.problemExplanation = problemExplanation;
        this.useYear = useYear;
        this.save = save;
        this.complete = complete;
    }
}
