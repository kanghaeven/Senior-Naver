package com.ssafy.seniornaver.mz.controller;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import com.ssafy.seniornaver.mz.dto.request.ProblemCreateRequestDto;
import com.ssafy.seniornaver.mz.dto.request.ProblemEvaluationRequestDto;
import com.ssafy.seniornaver.mz.dto.request.ProblemListRequestDto;
import com.ssafy.seniornaver.mz.dto.response.ProblemDetailResponseDto;
import com.ssafy.seniornaver.mz.dto.response.ProblemListResponseDto;
import com.ssafy.seniornaver.mz.dto.response.RandomProblemResponseDto;
import com.ssafy.seniornaver.mz.dto.response.TotalEvaluationResponseDto;
import com.ssafy.seniornaver.mz.service.SituationProblemService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Tag(name = "Problem", description = "문제 관련 서비스")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/problem/")
public class SituationProblemController {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final SituationProblemService situationProblemService;

    @Operation(summary = "단어 확인", description = "문제 생성이 가능한 단어인지 확인합니다.")
    @PostMapping("valid/{word}/{year}")
    public ResponseEntity wordCheck(@PathVariable("word") String word, @PathVariable("year") int year) {
        if (!situationProblemService.wordCheck(word, year)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("연도가 일치하지 않습니다.");
        }

        return ResponseEntity.ok("작성 가능한 단어입니다.");
    }

    @Operation(summary = "문제 등록", description = "문제를 등록합니다. 로그인이 된 상태여야 합니다.")
    @PostMapping(value = "register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createProblem(@RequestPart(name = "multipartFile") MultipartFile multipartFile,
                                        @RequestPart(name = "requestDto") ProblemCreateRequestDto problemCreateRequestDto,
                                        HttpServletRequest httpServletRequest) throws IOException {

        situationProblemService.relTagToProblem(
                situationProblemService.createProblem(problemCreateRequestDto, multipartFile, getMember(httpServletRequest)), problemCreateRequestDto);

        return ResponseEntity.ok("등록 완료");
    }

    @Operation(summary = "문제 리스트 목록", description = "로그인 되어있을경우 푼 문제, 저장한 문제를 구분합니다.")
    @GetMapping("v1/list")
    public ResponseEntity<List<ProblemListResponseDto>> getMemberProblemList(ProblemListRequestDto problemListRequestDto,
                                                                             HttpServletRequest httpServletRequest) {

        List<ProblemListResponseDto> problemListResponseDto;
        if (httpServletRequest.getHeader("Authorization") != null) {
            problemListResponseDto = situationProblemService.getMemberProblemList(problemListRequestDto, getMember(httpServletRequest));
        } else {
            problemListResponseDto = situationProblemService.getProblemList(problemListRequestDto);
        }

        return ResponseEntity.ok(problemListResponseDto);
    }

    @Operation(summary = "문제 상세", description = "랜덤으로 ProblemId 값을 5개 반환합니다.")
    @GetMapping("v1/detail/{id}")
    public ResponseEntity<ProblemDetailResponseDto> getMemberProblemList(@PathVariable("id") Long id) {
        return ResponseEntity.ok(situationProblemService.getProblemDetail(id));
    }
    
    @Operation(summary = "랜덤 문제 번호", description = "랜덤으로 ProblemId 값을 5개 반환합니다.")
    @GetMapping("v1/random/{year}")
    public ResponseEntity<RandomProblemResponseDto> getMemberProblemList(@PathVariable("year") int year) {
        return ResponseEntity.ok(situationProblemService.getRandomProblem(year));
    }

    @Operation(summary = "문제 저장", description = "랜덤 문제 간 추후에 풀어보고 싶은 문제를 개별로 저장합니다.")
    @PostMapping("scrap/{id}")
    public ResponseEntity saveProblem(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        situationProblemService.saveProblem(id, getMember(httpServletRequest));

        return ResponseEntity.ok("저장 완료");
    }

    @Operation(summary = "문제 저장 취소", description = "저장한 문제를 삭제합니다.")
    @DeleteMapping("scrap/cancel/{id}")
    public ResponseEntity cancelProblem(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        situationProblemService.cancelProblem(id, getMember(httpServletRequest));

        return ResponseEntity.ok("저장 취소 완료");
    }

    @Operation(summary = "문제 삭제", description = "{Admin 전용} 해당 문제를 삭제합니다.")
    @PostMapping("removal/{id}")
    public ResponseEntity deleteProblem(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        situationProblemService.deleteProblem(id, getMember(httpServletRequest));
        return ResponseEntity.ok("문제 삭제 완료");
    }

    @Operation(summary = "문제 풀이 결과 저장", description = "문제 풀이 후 결과를 저장합니다. 로그인 특전입니다.")
    @PostMapping("register/result")
    public ResponseEntity saveProblemResult(@RequestBody ProblemEvaluationRequestDto problemEvaluationRequestDto,
                                            HttpServletRequest httpServletRequest) {
        situationProblemService.problemEvaluation(problemEvaluationRequestDto, getMember(httpServletRequest));
        return ResponseEntity.ok("결과 저장 완료");
    }

    @Operation(summary = "전체 문제 풀이 결과", description = "5개의 문제를 모두 풀이 후 결과를 반환합니다.")
    @PostMapping("total/result")
    public ResponseEntity<TotalEvaluationResponseDto> saveProblemResult(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(situationProblemService.totalEvaluation(getMember(httpServletRequest)));
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
