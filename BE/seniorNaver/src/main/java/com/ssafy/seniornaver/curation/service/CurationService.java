package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.curation.dto.MZWordDto;

import java.util.List;

public interface CurationService {
    List<MZWordDto> getCarouselMzWords();
}
