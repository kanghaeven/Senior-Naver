package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.SituationProblem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SituationRepository extends JpaRepository<SituationProblem, Long> {
    List<SituationProblem> findAllByTitleContaining(String keyword, Pageable pageable);
    Optional<SituationProblem> findByProblemIdAndTitle(Long id, String title);
    boolean existsByTitle(String title);
    @Query(value = "SELECT * FROM seniornaver.situation_problem as sp " +
            "where sp.use_year >= :year " +
            "and sp.use_year < :year + 10 " +
            "order by RAND() limit 5", nativeQuery = true)
    List<SituationProblem> findCustom(@Param("year") int year);
}
