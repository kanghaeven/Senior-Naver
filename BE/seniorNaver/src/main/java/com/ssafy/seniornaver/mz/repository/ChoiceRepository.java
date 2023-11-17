package com.ssafy.seniornaver.mz.repository;

import com.ssafy.seniornaver.mz.entity.Choice;
import com.ssafy.seniornaver.mz.entity.SituationProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findAllBySituationProblem(SituationProblem situationProblem);
}
