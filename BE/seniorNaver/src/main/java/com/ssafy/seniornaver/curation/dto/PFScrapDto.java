package com.ssafy.seniornaver.curation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PFScrapDto {
    private String memberId;
    private String pfId;
    private String pfName;
    private String poster;

    @Builder
    public PFScrapDto(String memberId, String pfId, String pfName, String poster){
        this.memberId = memberId;
        this.pfId = pfId;
        this.pfName = pfName;
        this.poster = poster;
    }
}