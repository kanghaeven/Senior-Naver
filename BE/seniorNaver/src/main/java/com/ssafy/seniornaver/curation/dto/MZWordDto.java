package com.ssafy.seniornaver.curation.dto;

import com.ssafy.seniornaver.mz.entity.Dictionary;
import lombok.Getter;

@Getter
public class MZWordDto {

    private String word;
    private Long wordId;

    public MZWordDto(Dictionary dictionary) {
        this.word = dictionary.getWord();
        this.wordId = dictionary.getWordId();
    }
}
