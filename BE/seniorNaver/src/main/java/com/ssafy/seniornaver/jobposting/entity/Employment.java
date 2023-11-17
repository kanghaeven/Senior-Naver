package com.ssafy.seniornaver.jobposting.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String acceptMethod;

    @Column
    private String deadline;

    @Column
    private String employShape;

    @Column
    private String jobId;

    @Column
    private String jobClass;

    @Column
    private String title;

    @Column
    private String workPlace;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Builder
    public Employment(String acceptMethod, String deadline, String employShape, String jobId, String jobClass,
                      String title, String workPlace, LocalDate startDate, LocalDate endDate) {

        this.acceptMethod = acceptMethod;
        this.deadline = deadline;
        this.employShape = employShape;
        this.jobId = jobId;
        this.jobClass = jobClass;
        this.title = title;
        this.workPlace = workPlace;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
