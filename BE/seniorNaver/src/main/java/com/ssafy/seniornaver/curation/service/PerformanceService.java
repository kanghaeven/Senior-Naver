package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.curation.dto.PerformanceDetailListDto;
import com.ssafy.seniornaver.curation.dto.PerformanceDto;
import com.ssafy.seniornaver.curation.entity.Performance;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface PerformanceService {
    List<Performance> savePerformanceList() throws Exception;

    Mono<PerformanceDetailListDto> getPerformanceDetail(String pfid);

    List<PerformanceDto> getPerformancesNearDate();
}
