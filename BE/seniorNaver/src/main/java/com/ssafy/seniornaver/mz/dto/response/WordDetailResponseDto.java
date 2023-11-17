package com.ssafy.seniornaver.mz.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class WordDetailResponseDto {
    private String word;
    private String mean;
    private String example;
    private Integer useYear;
    private boolean scrap;
    private List<String> tags;
    private long totalScrap;
    private Map<String, Long> relProblem;

    @Builder
    public WordDetailResponseDto(String word, String mean, String example, Integer useYear, boolean scrap,
                                 long total, List<String> tags, Map<String, Long> relProblem) {

        this.word = word;
        this.mean = mean;
        this.example = example;
        this.useYear = useYear;
        this.totalScrap = total;
        this.scrap = scrap;
        this.tags = tags;
        this.relProblem = relProblem;
    }
}
