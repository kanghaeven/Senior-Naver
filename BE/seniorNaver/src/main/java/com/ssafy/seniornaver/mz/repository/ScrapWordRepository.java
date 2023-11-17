package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.Dictionary;
import com.ssafy.seniornaver.mz.entity.ScrapWord;
import com.ssafy.seniornaver.mz.entity.VocabularyList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapWordRepository extends JpaRepository<ScrapWord, Long> {
    List<ScrapWord> findAllByVocaId(VocabularyList vocabularyList, Pageable pageable);
    List<ScrapWord> findAllByVocaId(VocabularyList vocabularyList);
    boolean existsByWordIdAndVocaId(Dictionary wordId, VocabularyList vocaId);
    List<ScrapWord> findAllByWordId(Dictionary wordId);
    Optional<ScrapWord> findByWordIdAndVocaId(Dictionary wordId, VocabularyList vocaId);
}
