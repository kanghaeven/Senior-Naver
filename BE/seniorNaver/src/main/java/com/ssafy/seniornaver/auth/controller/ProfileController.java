package com.ssafy.seniornaver.auth.controller;

import com.ssafy.seniornaver.auth.dto.Request.KeywordRequestDto;
import com.ssafy.seniornaver.auth.dto.Request.NicknameRequestDto;
import com.ssafy.seniornaver.auth.dto.Request.RegionRequestDto;
import com.ssafy.seniornaver.auth.dto.Request.UpdateProfilePictureDto;
import com.ssafy.seniornaver.auth.dto.Response.MemberResponseDto;
import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.auth.service.MemberService;
import com.ssafy.seniornaver.auth.service.ProfileService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final MemberService memberService;
    private final ProfileService profileService;
    private final ScrapService scrapService;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;


    @PutMapping(value = "/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "프로필사진 변경", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<UpdateProfilePictureDto> updateProfile(@RequestPart(value="file", required = false) MultipartFile file, HttpServletRequest httpServletRequest) throws Exception {
        Member member = getMember(httpServletRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.updateProfilePicture(file, member.getMemberId()));
    }

    @GetMapping("/myProfile")
    @Operation(summary = "유저정보 가져오기", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<MemberResponseDto> getMemberInfo(HttpServletRequest httpServletRequest){
        Member member = getMember(httpServletRequest);
        return ResponseEntity.ok(memberService.getMemberInfo(member.getMemberId()));
    }

    @PutMapping(value="/nickname")
    @Operation(summary = "닉네임 변경", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<String> updateNickname(@RequestBody NicknameRequestDto nicknameRequestDto, HttpServletRequest httpServletRequest){
        Member member = getMember(httpServletRequest);
        return ResponseEntity.ok(profileService.updateNickname(member.getMemberId(),nicknameRequestDto.getNickname()));
    }

    @PutMapping(value="/region")
    @Operation(summary = "지역 변경", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<String> updateregion(@RequestBody RegionRequestDto regionRequestDto, HttpServletRequest httpServletRequest){
        Member member = getMember(httpServletRequest);
        return ResponseEntity.ok(profileService.updateRegion(member.getMemberId(),regionRequestDto.getRegion()));
    }

    @PutMapping(value="/keywords")
    @Operation(summary = "키워드 변경", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<List<String>> updateKeywords(@RequestBody KeywordRequestDto keywordRequestDto, HttpServletRequest httpServletRequest){
        Member member = getMember(httpServletRequest);
        return ResponseEntity.ok(profileService.updateKeywords(member.getMemberId(),keywordRequestDto.getKeywords()));
    }

    @GetMapping(value = "/getNewsScrap")
    @Operation(summary = "뉴스 스크랩 가져오기", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<List<NewsScrapDto>> getNewsScrap(HttpServletRequest httpServletRequest) {
        Member member = getMember(httpServletRequest);
        return ResponseEntity.ok(scrapService.getNewsScrap(member.getMemberId()));
    }

    @GetMapping(value = "/getPFScrap")
    @Operation(summary = "공연 스크랩 가져오기", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<List<PFScrapDto>> getPFScrap(HttpServletRequest httpServletRequest) {
        Member member = getMember(httpServletRequest);
        return ResponseEntity.ok(scrapService.getPFScrap(member.getMemberId()));
    }

    @GetMapping(value = "/getTourScrap")
    @Operation(summary = "관광 스크랩 가져오기", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<List<TourScrapDto>> getTourScrap(HttpServletRequest httpServletRequest) {
        Member member = getMember(httpServletRequest);
        return ResponseEntity.ok(scrapService.getTourScrap(member.getMemberId()));
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
