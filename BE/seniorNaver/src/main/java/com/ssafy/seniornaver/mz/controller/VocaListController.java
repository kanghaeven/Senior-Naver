package com.ssafy.seniornaver.mz.controller;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import com.ssafy.seniornaver.mz.dto.request.VocaListRequestDto;
import com.ssafy.seniornaver.mz.dto.response.ProblemEvaluationListResponseDto;
import com.ssafy.seniornaver.mz.dto.response.VocaListResponseDto;
import com.ssafy.seniornaver.mz.service.VocabularyListService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "VocabularyList", description = "단어장 관련 서비스")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/voca")
public class VocaListController {

    private final VocabularyListService vocabularyListService;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Operation(summary = "단어장 생성", description = "단어장이 없는 유저일 경우 단어장을 생성합니다.")
    @PostMapping("/register")
    public void createVocaList(HttpServletRequest httpServletRequest) {

        vocabularyListService.createVocaList(getMember(httpServletRequest));
    }

    @Operation(summary = "단어장 목록 불러오기", description = "category | 1 = 스크랩 단어,  2 = 저장 문제 , 3 = 만든 문제 " +
            " | 페이지는 0부터 시작")
    @PostMapping("/list")
    public ResponseEntity<VocaListResponseDto> getVocaList(@RequestBody VocaListRequestDto vocaListRequestDto,
                                                                HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(vocabularyListService.getVocaList(getMember(httpServletRequest), vocaListRequestDto));
    }

    @Operation(summary = "풀었던 문제 리스트 목록", description = "category 4, 기존과 반환 내용이 다르기 때문에 api 다르게 사용합니다.")
    @PostMapping("/list/result")
    public ResponseEntity<ProblemEvaluationListResponseDto> getScrapWordList(@RequestBody VocaListRequestDto vocaListRequestDto,
                                                                             HttpServletRequest httpServletRequest) {

        if (vocaListRequestDto.getCategory() != 4) { ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 api 요청 입니다."); }

        return ResponseEntity.ok(vocabularyListService.getResultList(getMember(httpServletRequest), vocaListRequestDto));
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
