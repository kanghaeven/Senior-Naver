package com.ssafy.seniornaver.curation.dto;

import lombok.Getter;

@Getter
public class PlaceDto {
    // 관광지로 변경합니다.
    private String firstimage;
    private String addr1;
    private String title;
    private String contentid;

    public PlaceDto(TourDtDto tourDtDto) {
        this.firstimage = tourDtDto.getFirstimage();
        this.addr1 = tourDtDto.getAddr1();
        this.title = tourDtDto.getTitle();
        this.contentid = tourDtDto.getContentid();
    }

//    private String thumbnail;
//    private String addressName;
//    private String placeName;
//    private String id;
//
//    public PlaceDto(SearchResponseDto.Document document) {
//        this.thumbnail = document.getThumbnail();
//        this.addressName = document.getAddress_name();
//        this.placeName = document.getPlace_name();
//        this.id = document.getId();
//    }
}
