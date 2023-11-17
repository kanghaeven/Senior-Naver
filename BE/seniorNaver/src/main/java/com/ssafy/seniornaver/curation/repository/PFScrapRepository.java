package com.ssafy.seniornaver.curation.repository;

import com.ssafy.seniornaver.curation.entity.PFScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PFScrapRepository extends JpaRepository<PFScrap,String> {
    Optional<PFScrap> findByMemberIdAndPfId(String memberId, String pfId);

    List<PFScrap> findByMemberId(String memberId);
}
