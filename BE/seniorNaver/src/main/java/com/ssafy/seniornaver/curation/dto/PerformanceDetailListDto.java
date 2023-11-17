package com.ssafy.seniornaver.curation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "dbs")
@XmlAccessorType(XmlAccessType.FIELD)
public class PerformanceDetailListDto {

    @XmlElement(name = "db")
    private List<PerformanceDetailDto> db;

}
