package com.ssafy.seniornaver.mz.service;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.mz.dto.request.ProblemCreateRequestDto;
import com.ssafy.seniornaver.mz.dto.request.ProblemEvaluationRequestDto;
import com.ssafy.seniornaver.mz.dto.request.ProblemListRequestDto;
import com.ssafy.seniornaver.mz.dto.response.ProblemDetailResponseDto;
import com.ssafy.seniornaver.mz.dto.response.ProblemListResponseDto;
import com.ssafy.seniornaver.mz.dto.response.RandomProblemResponseDto;
import com.ssafy.seniornaver.mz.dto.response.TotalEvaluationResponseDto;
import com.ssafy.seniornaver.mz.entity.SituationProblem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SituationProblemService {
    boolean wordCheck(String word, int year);
    SituationProblem createProblem(ProblemCreateRequestDto problemCreateRequestDto, MultipartFile multipartFile, Member member) throws IOException;
    void relTagToProblem(SituationProblem situationProblem, ProblemCreateRequestDto problemCreateRequestDto);
    List<ProblemListResponseDto> getMemberProblemList(ProblemListRequestDto problemListRequestDto, Member member);
    List<ProblemListResponseDto> getProblemList(ProblemListRequestDto problemListRequestDto);
    ProblemDetailResponseDto getProblemDetail(Long problemId);
    RandomProblemResponseDto getRandomProblem(int year);
    void saveProblem(Long id, Member member);
    void cancelProblem(Long id, Member member);
    void deleteProblem(Long id, Member member);
    void problemEvaluation(ProblemEvaluationRequestDto problemEvaluationRequestDto, Member member);
    TotalEvaluationResponseDto totalEvaluation(Member member);
}