package com.ssafy.seniornaver.curation.controller;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.curation.dto.*;
import com.ssafy.seniornaver.curation.entity.Performance;
import com.ssafy.seniornaver.curation.service.CurationService;
import com.ssafy.seniornaver.curation.service.NewsService;
import com.ssafy.seniornaver.curation.service.PerformanceService;
import com.ssafy.seniornaver.curation.service.TourDtService;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/curation/v1")
public class CurationController {

    private final PerformanceService performanceService;
    private final TourDtService tourDtService;
    private final NewsService newsService;
    private final CurationService curationService;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Operation(summary = "캐러셀에 필요한 데이터를 한번에 가져옵니다.", security = @SecurityRequirement(name = "Bearer"))
    @GetMapping("/carousel")
    public ResponseEntity<CarouselDto> getCarouselData(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        List<CurationDto> curations;
        List<MZWordDto> mzWords;
        List<PlaceDto> places;

        // mzWords 는 로그인과 상관없습니다.
        mzWords = curationService.getCarouselMzWords();

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 로그인한 사용자
//            String token = authHeader.substring(7);
            // 토큰을 이용하여 사용자 정보를 가져옵니다.
//            String memberId = jwtProvider.get(token).get("memberId", String.class); // JwtProvider는 토큰을 처리하는 클래스입니다.

            // 위는 memberId만, 아래는 getMember메서를 사용하여 member 객체를 가져옵니다.
            Member member = getMember(request);
            String region = member.getRegion();

            // 사용자 정보를 이용하여 필요한 데이터를 가져옵니다.
            curations = newsService.getCarouselNews(member);
            places = tourDtService.getCarouselPlaces(region);
        } else {
            // 로그인하지 않은 사용자
            // 기본 정보를 제공합니다.
            curations = newsService.getCarouselNews(null);
            places = tourDtService.getCarouselPlaces(null);
        }

        CarouselDto carouselDto = CarouselDto.builder()
                .curations(curations)
                .mzWords(mzWords)
                .places(places)
                .build();
        return ResponseEntity.ok(carouselDto);
    }

    @Operation(summary = "요청날짜 기준 공연 시작일이 가까운 순으로 공연목록 전체 반환")
    @GetMapping("/performance")
    public ResponseEntity<List<PerformanceDto>> getPerformances() {
        List<PerformanceDto> performances = performanceService.getPerformancesNearDate();
        return ResponseEntity.ok(performances);
    }
    @Operation(summary = "요청날짜 기준 7일간 공연목록 조회 후 DB에 저장 *1시간에 한번씩 실행*")
    @GetMapping("/performance/save")
    public List<Performance> savePerformanceList() throws Exception {
        return performanceService.savePerformanceList();
    }

    @Operation(summary = "공연ID를 통해 공연 상세정보 조회")
    @GetMapping("/performance/{pfid}")
    public Mono<ResponseEntity<PerformanceDetailDto>> getPerformanceDetail(
            @PathVariable("pfid")
            @Parameter(description = "공연 ID", example = "PF12345")
            String pfid
    ) {
        return performanceService.getPerformanceDetail(pfid)
                .map(performanceDetailListDto -> {
                    if (performanceDetailListDto.getDb() != null && !performanceDetailListDto.getDb().isEmpty()) {
                        PerformanceDetailDto performanceDetailDto = performanceDetailListDto.getDb().get(0);
                        return ResponseEntity.ok(performanceDetailDto);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                });
    }

    @Operation(summary = "지역코드를 통해 해당 지역의 관광지 목록 조회")
    @GetMapping("/tourdt/{areaCode}")
    public ResponseEntity<List<TourDtDto>> getTourDts(
            @Parameter(description = "지역 코드 (1:서울, 2:인천, 3:대전, 4:대구, 5:광주, 6:부산, 7:울산, 8:세종특별자치시, 31:경기도, 32:강원특별자치도, 33:충청북도, 34:충청남도, 35:경상북도, 36:경상남도, 37:전라북도, 38:전라남도, 39:제주도)")
            @PathVariable int areaCode) {
        List<TourDtDto> tourDtList = tourDtService.getTourDtList(areaCode);
        return ResponseEntity.ok(tourDtList);
    }

    @Operation(summary = "관광지 ID를 통해 해당 관광지의 상세 정보 조회")
    @GetMapping("/tourdt/detail/{contentId}")
    public ResponseEntity<TourDtDetail> getTourDtDetail(
            @Parameter(description = "관광지 ID")
            @PathVariable int contentId) {
        TourDtDetail tourDtDetail = tourDtService.getTourDtDetail(contentId);
        return ResponseEntity.ok(tourDtDetail);
    }

    @Operation(summary = "키워드를 통해 뉴스 목록 조회")
    @GetMapping("/news/{keyword}")
    public ResponseEntity<List<NewsDto>> getNewsList(
            @Parameter(description = "키워드, ex : 정치, 금융, IT, 스포츠 등 카테고리 입력")
            @PathVariable String keyword) {
        List<NewsDto> newsList = newsService.getNewsList(keyword);
        return ResponseEntity.ok(newsList);
    }

    // profileController에서 가져온 httpServletRequest의 헤더에 있는 토큰을 이용하여 사용자 정보를 가져오는 메서드
    private Member getMember(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        String bearer = header.substring(7);

        String memberId;
        try {
            memberId = (String) jwtProvider.get(bearer).get("memberId");
        } catch (ExpiredJwtException e) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID);
        }

        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID);
        });
        return member;
    }
}
