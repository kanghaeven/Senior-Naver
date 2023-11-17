package com.ssafy.seniornaver.mz.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TagToProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Tag tagId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SituationProblem problemId;

    @Builder
    public TagToProblem(Tag tagId, SituationProblem problemId) {
        this.tagId = tagId;
        this.problemId = problemId;
    }
}
