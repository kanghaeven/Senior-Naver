package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.CompleteProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompleteProblemRepository extends JpaRepository<CompleteProblem, Long> {
    List<CompleteProblem> findAllByVocaId(Long vocaId);
}
