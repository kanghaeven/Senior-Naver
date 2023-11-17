package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.Tag;
import com.ssafy.seniornaver.mz.entity.TagToProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagToProblemRepository extends JpaRepository<TagToProblem, Long> {
    List<TagToProblem> findAllByTagId(Tag tag);
}
