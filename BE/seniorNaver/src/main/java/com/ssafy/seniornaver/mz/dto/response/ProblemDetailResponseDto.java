package com.ssafy.seniornaver.mz.dto.response;

import com.ssafy.seniornaver.mz.entity.Choice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProblemDetailResponseDto {
    private Long problemId;
    private String title;
    private String image;
    private int answer;
    private String review;
    private String problemExplanation;
    private int useYear;
    private List<Choice> choices;

    @Builder
    public ProblemDetailResponseDto(Long problemId, String title, String image, int answer, String review,
                                    String problemExplanation, int useYear, List<Choice> choices) {
        this.problemId = problemId;
        this.title = title;
        this.image = image;
        this.answer = answer;
        this.review = review;
        this.problemExplanation = problemExplanation;
        this.useYear = useYear;
        this.choices = choices;
    }
}
