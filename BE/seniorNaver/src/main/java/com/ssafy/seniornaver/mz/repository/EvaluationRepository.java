package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.EvaluationResult;
import com.ssafy.seniornaver.mz.entity.VocabularyList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<EvaluationResult, Long> {
    List<EvaluationResult> findAllByVocaId(VocabularyList vocabularyList);
}
