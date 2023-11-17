package com.ssafy.seniornaver.auth.dto.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegionRequestDto {
    private String region;

    @Builder
    public RegionRequestDto(String region) {
        this.region = region;
    }
}
