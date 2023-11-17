package com.ssafy.seniornaver.location.service;

import com.ssafy.seniornaver.location.dto.LoadImageData;
import com.ssafy.seniornaver.location.dto.request.CategorySearchRequestDto;
import com.ssafy.seniornaver.location.dto.request.KeywordSearchRequestDto;
import com.ssafy.seniornaver.location.dto.response.SearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceSearchServiceImpl implements PlaceSearchService {

    private final WebClient webClient;
    @Value("${kakao.rest-api.key}") private String REST_API_KEY;

    static String keywordUri = "https://dapi.kakao.com/v2/local/search/keyword.json";
    static String categoryUri = "https://dapi.kakao.com/v2/local/search/category.json";
    static String imageUri = "https://dapi.kakao.com/v2/search/image";

    @Override
    public SearchResponseDto keywordSearch(KeywordSearchRequestDto requestSearchDto) {
        SearchResponseDto searchResponseDto = getKeywordData(keywordUri, requestSearchDto);
        updateSearchData(searchResponseDto);

        return searchResponseDto;
    }

    @Override
    public SearchResponseDto categorySearch(CategorySearchRequestDto requestSearchDto) {
        SearchResponseDto searchResponseDto = getCategoryData(categoryUri, requestSearchDto);
        updateSearchData(searchResponseDto);

        return searchResponseDto;
    }

    @Override
    public LoadImageData imageSearch(String baseUrl, String keyword) {
        return webClient.mutate()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "KakaoAK " + REST_API_KEY)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", keyword)
                        .queryParam("page", 1)
                        .queryParam("size", 1)
                        .build())
                .retrieve()
                .bodyToMono(LoadImageData.class)
                .block();
    }

    @Override
    public SearchResponseDto getKeywordData(String baseUrl, KeywordSearchRequestDto keywordSearchRequestDto) {
        return webClient.mutate()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "KakaoAK " + REST_API_KEY)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", keywordSearchRequestDto.getKeyword())
                        .queryParam("page", keywordSearchRequestDto.getPage())
                        .queryParam("size", 10)
                        .build())
                .retrieve()
                .bodyToMono(SearchResponseDto.class)
                .block();
    }

    @Override
    public SearchResponseDto getCategoryData(String baseUrl, CategorySearchRequestDto categorySearchRequestDto) {
        return webClient.mutate()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "KakaoAK " + REST_API_KEY)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("category_group_code", categorySearchRequestDto.getCategory())
                        .queryParam("page", categorySearchRequestDto.getPage())
                        .queryParam("x", categorySearchRequestDto.getX())
                        .queryParam("y", categorySearchRequestDto.getY())
                        .queryParam("size", 10)
                        .build())
                .retrieve()
                .bodyToMono(SearchResponseDto.class)
                .block();
    }

    private void updateSearchData(SearchResponseDto searchResponseDto) {
        for (int i = 0; i < searchResponseDto.getDocuments().size(); i++) {
            String categoryValue = searchResponseDto.getDocuments().get(i).getCategory_name();
            while (categoryValue.contains(">")) {
                categoryValue = categoryValue.substring(categoryValue.indexOf('>') + 1);
            }

            searchResponseDto.getDocuments().get(i).updateCategory(categoryValue);

            String placeName = searchResponseDto.getDocuments().get(i).getPlace_name();
            if (placeName.contains("<b>") || placeName.contains("</b>")) {
                placeName = placeName.replace("<b>", "");
                placeName = placeName.replace("</b>", "");
            }
            searchResponseDto.getDocuments().get(i).updatePlaceName(placeName);

            LoadImageData loadImageData = imageSearch(imageUri, searchResponseDto.getDocuments().get(i).getPlace_name()
                    + " " + searchResponseDto.getDocuments().get(i).getCategory_group_name());

            if (loadImageData.getDocuments().size() == 0) {
                continue;
            }

            if (loadImageData.getDocuments().get(0).getImage_url() == null) {
                searchResponseDto.getDocuments().get(i).updateThumbnail(loadImageData.getDocuments().get(0).getThumbnail_url());
                continue;
            }
            searchResponseDto.getDocuments().get(i).updateThumbnail(loadImageData.getDocuments().get(0).getImage_url());
        }
        searchResponseDto.getMeta().updateTotalPage(getTotalPage(searchResponseDto.getMeta().getPageable_count()));
    }

    // 전체 페이지 수 구하기
    private int getTotalPage(int totalCount) {
        int totalPage = 0;
        totalPage = totalCount / 10;
        if (totalCount % 10 != 0) {
            totalPage++;
        }
        return totalPage;
    }
}
