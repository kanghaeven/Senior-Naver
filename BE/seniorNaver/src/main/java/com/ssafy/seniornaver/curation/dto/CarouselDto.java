package com.ssafy.seniornaver.curation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CarouselDto {

    @Schema(description = "뉴스기사들")
    private List<CurationDto> curations;

    @Schema(description = "MZ단어들")
    private List<MZWordDto> mzWords;

    @Schema(description = "장소들")
    private List<PlaceDto> places;
}
