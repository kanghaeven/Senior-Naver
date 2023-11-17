package com.ssafy.seniornaver.mz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WordCreateRequestDto {
    private String word;
    private String mean;
    private String example;
    private int year;
    private List<String> wordTags;
}
