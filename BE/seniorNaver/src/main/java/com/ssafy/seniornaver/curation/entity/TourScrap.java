package com.ssafy.seniornaver.curation.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TourScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String title;

    @Column
    private String firstImage;

    @Column
    private String contentId;

    @Builder
    public TourScrap(String memberId, String title, String firstImage, String contentId){
        this.memberId = memberId;
        this.title = title;
        this.firstImage = firstImage;
        this.contentId = contentId;
    }
}
