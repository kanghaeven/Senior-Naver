package com.ssafy.seniornaver.curation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TourScrapDto {
    private String memberId;
    private String title;
    private String firstImage;
    private String contentId;

    @Builder
    public TourScrapDto(String memberId, String title, String firstImage, String contentId){
        this.memberId = memberId;
        this.title = title;
        this.firstImage = firstImage;
        this.contentId = contentId;
    }
}