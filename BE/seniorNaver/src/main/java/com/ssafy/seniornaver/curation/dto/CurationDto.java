package com.ssafy.seniornaver.curation.dto;

import lombok.Getter;

@Getter
public class CurationDto {
    private String imageUrl;
    private String title;
    private String link;

    public CurationDto(NewsDto newsDto) {
        this.imageUrl = newsDto.getImageUrl();
        this.title = newsDto.getTitle();
        this.link = newsDto.getLink();
    }
}
