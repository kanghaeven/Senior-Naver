package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.MakeProblem;
import com.ssafy.seniornaver.mz.entity.VocabularyList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MakeProblemRepository extends JpaRepository<MakeProblem, Long> {
    List<MakeProblem> findAllByVocaId(VocabularyList vocaId);
    List<MakeProblem> findAllByVocaId(VocabularyList vocaId, Pageable pageable);
}
