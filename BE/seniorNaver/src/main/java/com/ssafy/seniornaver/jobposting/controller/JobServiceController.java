package com.ssafy.seniornaver.jobposting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.seniornaver.error.response.ErrorResponse;
import com.ssafy.seniornaver.jobposting.dto.request.JobListSearchRequestDto;
import com.ssafy.seniornaver.jobposting.dto.response.JobDetailResponseDto;
import com.ssafy.seniornaver.jobposting.dto.response.JobListResponeDto;
import com.ssafy.seniornaver.jobposting.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Job", description = "일자리 관련 서비스")
@RestController
@RequestMapping("/job/v1")
@RequiredArgsConstructor
public class JobServiceController {

    private final JobService jobService;

    @Operation(summary = "일자리 데이터 DB 저장 (수동)", description = "안할 경우 오전 8시마다 갱신, (데이터 1일 1회 갱신한대서 한번만 함)")
    @GetMapping("/resource")
    public ResponseEntity saveData() throws JsonProcessingException {

        jobService.saveWorkList();

        return ResponseEntity.ok("DB저장 완료");
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "502", description = "BAD_GATEWAY", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @Operation(summary = "일자리 상세정보", description = "id -> jobId 요청, API응답입니다.")
    @GetMapping("/post/detail/{id}")
    public ResponseEntity<JobDetailResponseDto> jobDetail(@PathVariable String id) throws JsonProcessingException {

        JobDetailResponseDto jobDetailResponseDto = jobService.getDetailService(id);

        return ResponseEntity.ok(jobDetailResponseDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "502", description = "BAD_GATEWAY", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "일자리 목록", description = "page -> 0부터 시작, workPlc -> 항상 있을 것, keyword -> 없으면 '' -> 공백으로 보내기, 둘다 공백이면 전체 리스트")
    @GetMapping("/list")
    public ResponseEntity<JobListResponeDto> jobList(JobListSearchRequestDto jobListSearchRequestDto){

        JobListResponeDto jobListResponeDto = jobService.getJobList(jobListSearchRequestDto);

        return ResponseEntity.ok(jobListResponeDto);
    }
}
