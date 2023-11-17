package com.ssafy.seniornaver.jobposting.repository;

import com.ssafy.seniornaver.jobposting.entity.Employment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployRepository extends JpaRepository<Employment, Long> {
    boolean existsByJobId(String jobId);
    List<Employment> findAllByTitleContainingAndWorkPlaceLike(String title, String workPlace,Pageable pageable);
    List<Employment> findAllByTitleContainingAndWorkPlaceLike(String title, String workPlace);
    List<Employment> findAllByWorkPlaceLike(String workPlace, Pageable pageable);
    List<Employment> findAllByWorkPlaceLike(String workPlace);

    @Query(value = "delete FROM seniornaver.employment AS em WHERE end_date < DATE(NOW());", nativeQuery = true)
    void deleteAll();
}
