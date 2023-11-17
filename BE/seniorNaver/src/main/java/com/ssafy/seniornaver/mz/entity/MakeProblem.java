package com.ssafy.seniornaver.mz.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class MakeProblem extends ProblemBaseEntity{

    @ManyToOne
    @JoinColumn(nullable = false)
    private VocabularyList vocaId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SituationProblem problemId;

    public MakeProblem(VocabularyList vocaId, SituationProblem problemId) {
        this.problemId = problemId;
        this.vocaId = vocaId;
    }
}
