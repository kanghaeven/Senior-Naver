package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.VocabularyList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VocabularyListRepository extends JpaRepository<VocabularyList, Long> {
    Optional<VocabularyList> findByVocaId(Long vocaId);
    Optional<VocabularyList> findByMemberId(Long memberId);
}
