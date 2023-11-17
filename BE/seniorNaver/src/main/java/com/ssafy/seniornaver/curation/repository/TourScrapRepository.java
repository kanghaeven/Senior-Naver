package com.ssafy.seniornaver.curation.repository;

import com.ssafy.seniornaver.curation.entity.TourScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourScrapRepository extends JpaRepository<TourScrap,String> {
    Optional<TourScrap> findByMemberIdAndTitle(String memberId, String title);

    List<TourScrap> findByMemberId(String memberId);
}
