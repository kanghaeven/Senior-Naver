package com.ssafy.seniornaver.auth.repository;

import com.ssafy.seniornaver.auth.entity.Keyword;
import com.ssafy.seniornaver.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword,String> {
    List<Keyword> findByMember(Member member);
}
