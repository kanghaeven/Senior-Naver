package com.ssafy.seniornaver.curation.controller;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.curation.dto.NewsScrapDto;
import com.ssafy.seniornaver.curation.dto.PFScrapDto;
import com.ssafy.seniornaver.curation.dto.TourScrapDto;
import com.ssafy.seniornaver.curation.service.ScrapService;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap")
public class ScrapController {
    private final ScrapService scrapService;

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @PostMapping("/NewsScrap")
    @Operation(summary = "뉴스 스크랩", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<NewsScrapDto> newsScrap(@RequestBody NewsScrapDto newsScrapDto, HttpServletRequest httpServletRequest){
        Member member = getMember(httpServletRequest);
        newsScrapDto.setMemberId(member.getMemberId());
        return ResponseEntity.ok(scrapService.scrapNews(newsScrapDto));
    }

    @PostMapping("/PFScrap")
    @Operation(summary = "공연 스크랩", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<PFScrapDto> pfScrap(@RequestBody PFScrapDto pfScrapDto, HttpServletRequest httpServletRequest) {
        Member member = getMember(httpServletRequest);
        pfScrapDto.setMemberId(member.getMemberId());
        return ResponseEntity.ok(scrapService.scrapPF(pfScrapDto));
    }

    @PostMapping("/TourScrap")
    @Operation(summary = "관광 스크랩", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<TourScrapDto> tourScrap(@RequestBody TourScrapDto tourScrapDto, HttpServletRequest httpServletRequest) {
        Member member = getMember(httpServletRequest);
        tourScrapDto.setMemberId(member.getMemberId());
        return ResponseEntity.ok(scrapService.scrapTour(tourScrapDto));
    }

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
