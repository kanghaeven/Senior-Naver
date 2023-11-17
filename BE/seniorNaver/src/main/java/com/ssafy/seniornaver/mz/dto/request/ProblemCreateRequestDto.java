package com.ssafy.seniornaver.mz.dto.request;

import com.ssafy.seniornaver.mz.entity.Choice;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProblemCreateRequestDto{
    private String title;
    private String image;
    private int answer;
    private String review;
    private String problemExplanation;
    private int useYear;
    private String word;
    private List<String> tags;
    private List<Choice> choices;
}
