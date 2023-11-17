package com.ssafy.seniornaver.curation.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class NewsScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String title;

    @Column
    private String link;

    @Column
    private String imageUrl;

    @Builder
    public NewsScrap(String memberId, String title, String link, String imageUrl){
        this.memberId = memberId;
        this.title = title;
        this.link = link;
        this.imageUrl = imageUrl;
    }
}
