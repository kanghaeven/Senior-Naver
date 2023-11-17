package com.ssafy.seniornaver.jobposting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.seniornaver.jobposting.dto.request.JobListSearchRequestDto;
import com.ssafy.seniornaver.jobposting.dto.response.JobDetailResponseDto;
import com.ssafy.seniornaver.jobposting.dto.response.JobListResponeDto;

public interface JobService {
    JobDetailResponseDto getDetailService(String jobId) throws JsonProcessingException;
    void saveWorkList() throws JsonProcessingException;
    void deleteWorkList();
    JobListResponeDto getJobList(JobListSearchRequestDto jobListSearchRequestDto);
    String getListData(int pageNo);
    String getDetailData(String jobId);
}
