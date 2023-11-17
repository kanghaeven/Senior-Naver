package com.ssafy.seniornaver.curation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourDtDetail {

    @Schema(description = "관광지ID")
    private String contentid;
    @Schema(description = "관광지명")
    private String title;
    @Schema(description = "등록일")
    private String createdtime;
    @Schema(description = "수정일")
    private String modifiedtime;
    @Schema(description = "전화번호")
    private String tel;
    @Schema(description = "전화처")
    private String telname;
    @Schema(description = "홈페이지주소")
    private String homepage;
    @Schema(description = "대표이미지(원본) 500*333")
    private String firstimage;
    @Schema(description = "대표이미지(썸네일) 150*100")
    private String firstimage2;
    @Schema(description = "지역코드")
    private String areacode;
    @Schema(description = "시군구코드")
    private String sigungucode;
    @Schema(description = "주소")
    private String addr1;
    @Schema(description = "상세주소")
    private String addr2;
    @Schema(description = "우편번호")
    private String zipcode;
    @Schema(description = "x좌표")
    private String mapx;
    @Schema(description = "y좌표")
    private String mapy;
    @Schema(description = "Map Level")
    private String mlevel;
    @Schema(description = "관광지 개요")
    private String overview;

}
