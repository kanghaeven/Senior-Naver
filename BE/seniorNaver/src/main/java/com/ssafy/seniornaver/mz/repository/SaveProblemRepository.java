package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.SaveProblem;
import com.ssafy.seniornaver.mz.entity.SituationProblem;
import com.ssafy.seniornaver.mz.entity.VocabularyList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaveProblemRepository extends JpaRepository<SaveProblem, Long> {
    List<SaveProblem> findAllByVocaId(VocabularyList vocaId);
    List<SaveProblem> findAllByVocaId(VocabularyList vocaId, Pageable pageable);
    Optional<SaveProblem> findByProblemIdAndVocaId(SituationProblem situationProblem, VocabularyList vocabularyList);
}
