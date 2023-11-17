package com.ssafy.seniornaver.mz.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ScrapWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private VocabularyList vocaId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Dictionary wordId;

    @Builder
    public ScrapWord(VocabularyList vocaId, Dictionary wordId) {
        this.vocaId = vocaId;
        this.wordId = wordId;
    }
}
