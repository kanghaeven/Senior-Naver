package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.curation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourDtServiceImpl implements TourDtService{

    @Value("${tour-api.serviceKey}")
    private String serviceKey;

    private final WebClient webClient;

    private final MemberRepository memberRepository;

    private int mapRegionToAreaCode(String region) {
        switch (region) {
            case "서울":
                return 1;
            case "인천":
                return 2;
            case "대전":
                return 3;
            case "대구":
                return 4;
            case "광주":
                return 5;
            case "부산":
                return 6;
            case "울산":
                return 7;
            case "세종":
                return 8;
            case "경기":
                return 31;
            case "강원":
                return 32;
            case "충북":
                return 33;
            case "충남":
                return 34;
            case "경북":
                return 35;
            case "경남":
                return 36;
            case "전북":
                return 37;
            case "전남":
                return 38;
            case "제주":
                return 39;
            default:
                return 1;  // 기본값
        }
    }

    public List<TourDtDto> getTourDtList(int areaCode) {
        String url = "http://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=" + serviceKey +
                "&numOfRows=1000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentTypeId=12&areaCode="+ areaCode + "&_type=json&arrange=O";

        Mono<List<TourDtDto>> mono = this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(TourApiResponse.class)
                .map(tourApiResponse -> tourApiResponse.getResponse().getBody().getItems().getItem())
                // firstimage가 null이거나 비어있는 경우 제외
                .map(list -> list.stream()
                        .filter(item -> item.getFirstimage() != null && !item.getFirstimage().isEmpty())
                        .collect(Collectors.toList()));

        List<TourDtDto> tourDtList = mono.block();
        return tourDtList;
    }

    public TourDtDetail getTourDtDetail(int contendId){
        String url = "http://apis.data.go.kr/B551011/KorService1/detailCommon1?serviceKey=" + serviceKey + "&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&contentId="+ contendId +"&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y";

        return this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(TourDtDetailResponse.class)
                .map(tourDtDetailResponse -> tourDtDetailResponse.getResponse().getBody().getItems().getItem().get(0))
                .block();
    }

    @Override
    public List<PlaceDto> getCarouselPlaces(String region) {
        int areaCode = 1;  // 기본 지역 코드

        if (region != null) {
            areaCode = mapRegionToAreaCode(region);
        }

        List<TourDtDto> tourDtList = getTourDtList(areaCode);
        Collections.shuffle(tourDtList);

        // TourDtDto list를 PlaceDto list로 변환
        return tourDtList.stream()
                .map(PlaceDto::new)  // TourDtDto를 PlaceDto로 변환
                .limit(10)
                .collect(Collectors.toList());
    }


}
