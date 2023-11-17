package com.ssafy.seniornaver.mz.service;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.mz.dto.request.VocaListRequestDto;
import com.ssafy.seniornaver.mz.dto.response.ProblemEvaluationListResponseDto;
import com.ssafy.seniornaver.mz.dto.response.VocaListResponseDto;

public interface VocabularyListService {
    void createVocaList(Member memberId);
    VocaListResponseDto getVocaList(Member member, VocaListRequestDto vocaListRequestDto);
    ProblemEvaluationListResponseDto getResultList(Member member, VocaListRequestDto vocaListRequestDto);
}
