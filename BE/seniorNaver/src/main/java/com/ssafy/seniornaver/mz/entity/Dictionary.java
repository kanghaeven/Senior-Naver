package com.ssafy.seniornaver.mz.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String mean;

    @Column(nullable = false)
    private String example;

    @Column(nullable = false)
    private Integer useYear;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "id", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<TagToWord> wordTags = new ArrayList<>();

    @OneToMany(mappedBy = "scrapId", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<ScrapWord> scrapWordList = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.createAt = LocalDateTime.now().withNano(0);
    }

    @Builder
    public Dictionary(String word, String mean, String example, Integer useYear) {
        this.word = word;
        this.mean = mean;
        this.example = example;
        this.useYear = useYear;
    }

    public void updateDictionary(String mean, String example, Integer useYear) {
        this.mean = mean;
        this.example = example;
        this.useYear = useYear;
        this.updateAt = LocalDateTime.now().withNano(0);
    }
}
