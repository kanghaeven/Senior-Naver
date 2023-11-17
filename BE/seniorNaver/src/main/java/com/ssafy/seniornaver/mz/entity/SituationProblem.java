package com.ssafy.seniornaver.mz.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SituationProblem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    @Column(nullable = false)
    private String title;

    @Column
    private String image;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column
    private LocalDateTime updateAt;

    @Column(nullable = false)
    private int answer;

    @Column(nullable = false)
    private String review;

    @Column(nullable = false)
    private String problemExplanation;

    @Column(nullable = false)
    private int useYear;

    @Column(nullable = false)
    private String makeMember;

    @OneToMany(mappedBy = "tagId", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<TagToProblem> tags = new ArrayList<>();

    @OneToMany(mappedBy = "vocaId", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<CompleteProblem> completeVocaList = new ArrayList<>();

    @OneToMany(mappedBy = "vocaId", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<SaveProblem> saveMember = new ArrayList<>();

    @OneToMany(mappedBy = "choiceId", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Choice> choiceList = new ArrayList<>();

    @Builder
    public SituationProblem(String title, String image, int answer, String review,
                            String problemExplanation, int useYear, String makeMember) {
        this.title = title;
        this.image = image;
        this.answer = answer;
        this.review = review;
        this.makeMember = makeMember;
        this.problemExplanation = problemExplanation;
        this.useYear = useYear;
    }

    @PrePersist
    private void prePersist() {
        this.createAt = LocalDateTime.now().withNano(0);
    }

    public void updateProblem(String title, int answer, String review, String problemExplanation, int useYear) {
        this.title = title;
        this.answer = answer;
        this.review = review;
        this.problemExplanation = problemExplanation;
        this.useYear = useYear;
        this.updateAt = LocalDateTime.now().withNano(0);
    }
}
