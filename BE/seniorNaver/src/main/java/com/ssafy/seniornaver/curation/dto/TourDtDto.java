package com.ssafy.seniornaver.curation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourDtDto {
    // 관광지
    // TouristDestination이라서 TourDt

    @Schema(description = "주소")
    private String addr1;
    @Schema(description = "상세주소")
    private String addr2;
    @Schema(description = "등록일")
    private String createdtime;
    @Schema(description = "콘텐츠ID")
    private String contentid;
    @Schema(description = "대표이미지(원본) 500*333")
    private String firstimage;
    @Schema(description = "대표이미지(썸네일) 150*100")
    private String firstimage2;
    @Schema(description = "GPS X좌표")
    private String mapx;
    @Schema(description = "GPS Y좌표")
    private String mapy;
    @Schema(description = "수정일")
    private String modifiedtime;
    @Schema(description = "관광지이름")
    private String title;

}
