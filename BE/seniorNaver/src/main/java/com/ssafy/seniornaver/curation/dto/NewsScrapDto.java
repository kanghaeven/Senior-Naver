package com.ssafy.seniornaver.curation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsScrapDto {
    private String memberId;
    private String title;
    private String link;
    private String imageUrl;

    @Builder
    public NewsScrapDto(String memberId, String title, String link, String imageUrl){
        this.memberId = memberId;
        this.title = title;
        this.link = link;
        this.imageUrl = imageUrl;
    }
}
