package com.ssafy.seniornaver.curation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "db")
public class PerformanceDto {

    @Schema(description = "공연ID")
    private String pfId;

    @Schema(description = "공연명")
    private String performenceName;

    @Schema(description = "공연 시작일")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate startDate;

    @Schema(description = "공연 종료일")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate endDate;

    @Schema(description = "공연시설")
    private String theaterName;

    @Schema(description = "포스터이미지경로")
    private String poster;

    @Schema(description = "공연장르명")
    private String genre;

    @Schema(description = "공연상태")
    private String pfState;

    @Schema(description = "오픈런유무")
    private String openRun;

//    @XmlElement(name = "prfnm")
//    public void setPerformenceName(String performenceName) {
//        this.performenceName = performenceName;
//    }
//
//    @XmlJavaTypeAdapter(LocalDateAdapter.class)
//    @XmlElement(name = "prfpdfrom")
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    @XmlJavaTypeAdapter(LocalDateAdapter.class)
//    @XmlElement(name = "prfpdto")
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }
//
//    @XmlElement(name = "fcltynm")
//    public void setTheaterName(String theaterName) {
//        this.theaterName = theaterName;
//    }
//
//    @XmlElement(name = "poster")
//    public void setPoster(String poster) {
//        this.poster = poster;
//    }
}
