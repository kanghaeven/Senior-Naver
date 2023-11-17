package com.ssafy.seniornaver.curation.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PFScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String pfId;

    @Column
    private String pfName;

    @Column
    private String poster;

    @Builder
    public PFScrap(String memberId, String pfId, String pfName, String poster){
        this.memberId = memberId;
        this.pfId = pfId;
        this.pfName = pfName;
        this.poster = poster;
    }
}
