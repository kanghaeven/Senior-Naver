package com.ssafy.seniornaver.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId")
    private Member member;

    @Column
    private String keyword;

    public Keyword(String keyword, Member member) {
        this.keyword = keyword;
        this.member = member;
    }
}
