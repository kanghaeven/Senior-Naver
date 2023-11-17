package com.ssafy.seniornaver.curation.entity;

import com.ssafy.seniornaver.curation.dto.LocalDateAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "db")
public class Performance {

    @Id
    @Schema(description = "공연ID")
    private String pfId;

    @Schema(description = "공연명")
    private String pfName;

    @Schema(description = "공연 시작일")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate startDate;

    @Schema(description = "공연 종료일")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate endDate;

    @Schema(description = "공연시설")
    private String theater;

    @Schema(description = "포스터이미지경로")
    private String poster;

    @Schema(description = "공연장르명")
    private String genre;

    @Schema(description = "공연상태")
    private String pfState;

    @Schema(description = "오픈런유무")
    private String openRun;

    @XmlElement(name = "mt20id")
    public void setPfId(String pfId) {
        this.pfId = pfId;
    }

    @XmlElement(name = "prfnm")
    public void setPfName(String pfName) {
        this.pfName = pfName;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "prfpdfrom")
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "prfpdto")
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @XmlElement(name = "fcltynm")
    public void setTheater(String theater) {
        this.theater = theater;
    }

    @XmlElement(name = "poster")
    public void setPoster(String poster) {
        this.poster = poster;
    }

    @XmlElement(name = "genrenm")
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @XmlElement(name = "prfstate")
    public void setPfState(String pfState) {
        this.pfState = pfState;
    }

    @XmlElement(name = "openrun")
    public void setOpenRun(String openRun) {
        this.openRun = openRun;
    }
}
