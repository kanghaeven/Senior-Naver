package com.ssafy.seniornaver.curation.repository;

import com.ssafy.seniornaver.curation.entity.NewsScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsScrapRepository extends JpaRepository<NewsScrap,String> {
    Optional<NewsScrap> findByMemberIdAndTitle(String memberId, String title);

    List<NewsScrap> findByMemberId(String memberId);
}
