package com.ssafy.seniornaver.location.service;

import com.ssafy.seniornaver.location.dto.LoadImageData;
import com.ssafy.seniornaver.location.dto.request.CategorySearchRequestDto;
import com.ssafy.seniornaver.location.dto.request.KeywordSearchRequestDto;
import com.ssafy.seniornaver.location.dto.response.SearchResponseDto;

public interface PlaceSearchService {
    SearchResponseDto keywordSearch(KeywordSearchRequestDto requestSearchDto);
    SearchResponseDto categorySearch(CategorySearchRequestDto requestSearchDto);
    LoadImageData imageSearch(String baseUrl, String keyword);
    SearchResponseDto getKeywordData(String baseUrl, KeywordSearchRequestDto keywordSearchRequestDto);
    SearchResponseDto getCategoryData(String baseUrl, CategorySearchRequestDto categorySearchRequestDto);
}
