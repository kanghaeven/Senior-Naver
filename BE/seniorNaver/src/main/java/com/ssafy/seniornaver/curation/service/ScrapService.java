package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.curation.dto.NewsScrapDto;
import com.ssafy.seniornaver.curation.dto.PFScrapDto;
import com.ssafy.seniornaver.curation.dto.TourScrapDto;
import com.ssafy.seniornaver.curation.entity.NewsScrap;

import java.util.List;

public interface ScrapService {
    NewsScrapDto scrapNews(NewsScrapDto newsScrapDto);
    PFScrapDto scrapPF(PFScrapDto pfScrapDto);
    TourScrapDto scrapTour(TourScrapDto tourScrapDto);

    List<NewsScrapDto> getNewsScrap(String memberId);

    List<PFScrapDto> getPFScrap(String memberId);

    List<TourScrapDto> getTourScrap(String memberId);
}
