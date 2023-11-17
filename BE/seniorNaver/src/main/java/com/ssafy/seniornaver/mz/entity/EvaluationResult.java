package com.ssafy.seniornaver.mz.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class EvaluationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private VocabularyList vocaId;

    @Column(nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate createAt;

    @Column(nullable = false)
    private int choice;

    @Column(nullable = false)
    private boolean answer;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }

    @Builder
    public EvaluationResult(VocabularyList vocabularyList, Long problemId, String title, int choice, boolean answer) {
        this.vocaId = vocabularyList;
        this.problemId = problemId;
        this.title = title;
        this.choice = choice;
        this.answer = answer;
    }
}
