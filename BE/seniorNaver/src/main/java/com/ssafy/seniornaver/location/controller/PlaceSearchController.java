package com.ssafy.seniornaver.location.controller;

import com.ssafy.seniornaver.error.response.ErrorResponse;
import com.ssafy.seniornaver.location.dto.request.CategorySearchRequestDto;
import com.ssafy.seniornaver.location.dto.request.KeywordSearchRequestDto;
import com.ssafy.seniornaver.location.dto.response.SearchResponseDto;
import com.ssafy.seniornaver.location.service.PlaceSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "PlaceSearch", description = "장소 검색 (맛집, 관광명소, 병원)")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search/v1")
public class PlaceSearchController {

    private final PlaceSearchService searchService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "키워드 검색", description = "키워드를 통해 검색합니다.")
    @GetMapping("/keyword")
    public ResponseEntity<SearchResponseDto> keywordSearch(KeywordSearchRequestDto requestSearchDto) {

        SearchResponseDto responseSearchDto = searchService.keywordSearch(requestSearchDto);

        return ResponseEntity.ok(responseSearchDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "카테고리 검색", description = "자신의 지역기반으로 검색합니다.")
    @GetMapping("/category")
    public ResponseEntity<SearchResponseDto> categorySearch(CategorySearchRequestDto requestSearchDto) {

        SearchResponseDto responseSearchDto = searchService.categorySearch(requestSearchDto);

        return ResponseEntity.ok(responseSearchDto);
    }
}
