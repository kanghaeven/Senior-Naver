package com.ssafy.seniornaver.curation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDto {

    @Schema(description = "기사제목")
    private String title;
    @Schema(description = "원본링크")
    private String originallink;
    @Schema(description = "네이버링크")
    private String link;
    @Schema(description = "기사요약")
    private String description;
    @Schema(description = "게시시간")
    private String pubDate;
    @Schema(description = "기사이미지")
    private String imageUrl;

}
