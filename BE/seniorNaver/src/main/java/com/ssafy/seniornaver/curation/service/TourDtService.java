package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.curation.dto.PlaceDto;
import com.ssafy.seniornaver.curation.dto.TourDtDetail;
import com.ssafy.seniornaver.curation.dto.TourDtDto;

import java.util.List;

public interface TourDtService {
    List<TourDtDto> getTourDtList(int areaCode);

    TourDtDetail getTourDtDetail(int contentId);
    List<PlaceDto> getCarouselPlaces(String region);
}
