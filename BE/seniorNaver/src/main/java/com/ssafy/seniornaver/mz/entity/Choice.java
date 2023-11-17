package com.ssafy.seniornaver.mz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Choice {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choiceId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(referencedColumnName = "problemId")
    private SituationProblem situationProblem;

    @Column(nullable = false)
    private int choiceNum;

    @Column(nullable = false)
    private String content;

    @Builder
    public Choice(SituationProblem situationProblem, int choiceNum, String content) {
        this.situationProblem = situationProblem;
        this.choiceNum = choiceNum;
        this.content = content;
    }
}
