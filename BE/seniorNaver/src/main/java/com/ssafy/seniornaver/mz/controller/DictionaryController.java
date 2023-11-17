package com.ssafy.seniornaver.mz.controller;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import com.ssafy.seniornaver.mz.dto.request.DictionaryWordListRequestDto;
import com.ssafy.seniornaver.mz.dto.request.WordCreateRequestDto;
import com.ssafy.seniornaver.mz.dto.response.DictionaryWordListResponseDto;
import com.ssafy.seniornaver.mz.dto.response.WordDetailResponseDto;
import com.ssafy.seniornaver.mz.service.DictionaryService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Tag(name = "Dictionary", description = "사전관련 서비스")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionary/")
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Operation(summary = "전체 단어 조회", description = "ㄱㄴㄷ 순으로 단어 목록을 조회한다, page는 0부터 시작")
    @PostMapping("v1/word/list")
    public ResponseEntity<DictionaryWordListResponseDto> getWordList(@RequestBody DictionaryWordListRequestDto requestDto,
                                                                     HttpServletRequest httpServletRequest) {

        DictionaryWordListResponseDto dictionaryWordListResponseDto;
        String header = httpServletRequest.getHeader("Authorization");
        if (header == null || getMember(httpServletRequest).getVocaId() == null) {
            dictionaryWordListResponseDto = dictionaryService.getWordList(requestDto);
        } else {
            dictionaryWordListResponseDto = dictionaryService.getMemberWordList(requestDto, getMember(httpServletRequest));
        }

        return ResponseEntity.ok(dictionaryWordListResponseDto);
    }

    @Operation(summary = "사전에 단어 등록", description = "Admin 계정 전용, 추후 Admin 계정이 아닐 시 에러 반환 예정")
    @PostMapping("v1/word/register")
    public ResponseEntity createWord(@RequestBody WordCreateRequestDto wordCreateRequestDto) {

        dictionaryService.wordCreate(wordCreateRequestDto);

        return ResponseEntity.ok("단어 저장, 태깅 성공");
    }

    @Operation(summary = "단어 상세", description = "id -> wordId")
    @GetMapping("v1/word/{id}")
    public ResponseEntity<WordDetailResponseDto> wordDetail(@PathVariable("id") Long id,
                                    HttpServletRequest httpServletRequest) {

        WordDetailResponseDto wordDetailResponseDto;
        String header = httpServletRequest.getHeader("Authorization");
        if (header == null || getMember(httpServletRequest).getVocaId() == null) {
            wordDetailResponseDto = dictionaryService.getWordDetail(id, null);
        } else {
            wordDetailResponseDto = dictionaryService.getWordDetail(id, getMember(httpServletRequest).getVocaId());
        }
        return ResponseEntity.ok(wordDetailResponseDto);
    }

    @Operation(summary = "단어 스크랩", description = "단어장에 단어 추가. id는 단어의 id => wordId")
    @PostMapping("word/scrap/{id}")
    public ResponseEntity scrapWord(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {

        dictionaryService.wordScrap(getMember(httpServletRequest).getVocaId(), id);

        return ResponseEntity.ok("스크랩 성공");
    }

    @Operation(summary = "단어 스크랩 취소", description = "단어장이 있는 경우, 단어장에 단어 추가. id는 단어의 id => wordId")
    @DeleteMapping("word/cancel/{id}")
    public ResponseEntity unScrap(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {

        dictionaryService.unScrap(getMember(httpServletRequest).getVocaId(), id);

        return ResponseEntity.ok("스크랩 취소 성공");
    }

    @Operation(summary = "단어 삭제", description = "사전에 있는 단어를 삭제합니다.")
    @DeleteMapping("word/removal/{id}")
    public ResponseEntity deleteWord(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {

        dictionaryService.wordDelete(id, getMember(httpServletRequest));

        return ResponseEntity.ok("스크랩 취소 성공");
    }

    @Operation(summary = "오늘의 단어", description = "사전에 있는 단어 중 랜덤으로 1개의 단어를 반환합니다.")
    @GetMapping("v1/today/word")
    public ResponseEntity<Map<String, Long>> todayWord() {
        return ResponseEntity.ok(dictionaryService.todayWord());
    }

    @Operation(summary = "오늘의 단어 세팅", description = "강제 설정용")
    @GetMapping("v1/register/today/word")
    public void setTodayWord() { dictionaryService.setTodayWord(); }
    
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
