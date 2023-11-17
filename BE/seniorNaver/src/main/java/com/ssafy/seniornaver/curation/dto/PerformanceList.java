package com.ssafy.seniornaver.curation.dto;

import com.ssafy.seniornaver.curation.entity.Performance;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "dbs")
public class PerformanceList {
    private List<Performance> db;

    @XmlElement(name = "db")
    public List<Performance> getPerformances() {
        return db;
    }

    public void setPerformances(List<Performance> performances) {
        this.db = performances;
    }
}

