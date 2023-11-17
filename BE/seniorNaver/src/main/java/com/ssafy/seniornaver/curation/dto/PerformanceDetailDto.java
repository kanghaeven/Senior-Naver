package com.ssafy.seniornaver.curation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "db")
@XmlAccessorType(XmlAccessType.FIELD)
public class PerformanceDetailDto {

    @Schema(description = "공연ID")
    @XmlElement(name = "mt20id")
    private String mt20id;

    @Schema(description = "공연명")
    @XmlElement(name = "prfnm")
    private String prfnm;

    @Schema(description = "공연시작일")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "prfpdfrom")
    private LocalDate prfpdfrom;

    @Schema(description = "공연종료일")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "prfpdto")
    private LocalDate prfpdto;

    @Schema(description = "공연시설명(공연장명)")
    @XmlElement(name = "fcltynm")
    private String fcltynm;

    @Schema(description = "공연출연진")
    @XmlElement(name = "prfcast")
    private String prfcast;

    @Schema(description = "공연제작진")
    @XmlElement(name = "prfcrew")
    private String prfcrew;

    @Schema(description = "공연런타임")
    @XmlElement(name = "prfruntime")
    private String prfruntime;

    @Schema(description = "공연 관람 연령")
    @XmlElement(name = "prfage")
    private String prfage;

    @Schema(description = "제작사")
    @XmlElement(name = "entrpsnm")
    private String entrpsnm;

    @Schema(description = "티켓가격")
    @XmlElement(name = "pcseguidance")
    private String pcseguidance;

    @Schema(description = "포스터이미지경로")
    @XmlElement(name = "poster")
    private String poster;

    @Schema(description = "줄거리")
    @XmlElement(name = "sty")
    private String sty;

    @Schema(description = "지역")
    @XmlElement(name = "area")
    private String area;

    @Schema(description = "장르")
    @XmlElement(name = "genrenm")
    private String genrenm;

    @Schema(description = "오픈런")
    @XmlElement(name = "openrun")
    private String openrun;

    @Schema(description = "공연상태")
    @XmlElement(name = "prfstate")
    private String prfstate;

    @Schema(description = "소개이미지목록")
    @XmlElementWrapper(name = "styurls")
    @XmlElement(name = "styurl")
    private List<String> styUrlList;

    @Schema(description = "공연시설ID")
    @XmlElement(name = "mt10id")
    private String mt10id;

    @Schema(description = "공연시간")
    @XmlElement(name = "dtguidance")
    private String dtguidance;
}
