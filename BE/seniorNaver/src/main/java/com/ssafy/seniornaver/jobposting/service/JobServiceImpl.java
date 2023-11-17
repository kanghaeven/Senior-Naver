package com.ssafy.seniornaver.jobposting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ssafy.seniornaver.jobposting.dto.request.JobListRequestDto;
import com.ssafy.seniornaver.jobposting.dto.request.JobListSearchRequestDto;
import com.ssafy.seniornaver.jobposting.dto.response.JobDetailResponseDto;
import com.ssafy.seniornaver.jobposting.dto.response.JobListResponeDto;
import com.ssafy.seniornaver.jobposting.entity.Employment;
import com.ssafy.seniornaver.jobposting.repository.EmployRepository;
import com.ssafy.seniornaver.location.dto.LoadImageData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final EmployRepository employRepository;
    @Value("${data.job.api-key}") private String apiKey;
    @Value("${kakao.rest-api.key}") private String REST_API_KEY;

    /*
    * 상세검색 -> JobId로 상세내용 확인
    * */
    @Override
    public JobDetailResponseDto getDetailService(String jobId) throws JsonProcessingException {
        JSONObject post = xmlToJson(getDetailData(jobId)).getJSONObject("item");
        JobDetailResponseDto jobDetailResponseDto = objectMapper.readValue(post.toString(), JobDetailResponseDto.class);
        updateJobDetail(jobDetailResponseDto);

        return jobDetailResponseDto;
    }

    /*
    *  일자리 목록 DB에 저장하기
    * */
    @Override
    @Scheduled (cron = "0 0 8 * * ?", zone = "Asia/Seoul")
    @Transactional
    public void saveWorkList() throws JsonProcessingException {

        for (int i = 1; i < 3; i++) {
            JSONObject posts = xmlToJson(getListData(i));

            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            JobListRequestDto jobListRequestDto = objectMapper.readValue(posts.toString(), JobListRequestDto.class);

            List<Employment> employmentList = jobListRequestDto.getItem().stream()
                    .filter(item -> !employRepository.existsByJobId(item.getJobId()))
                    .filter(item -> !item.getDeadline().equals("마감"))
                    .map(item -> Employment.builder()
                            .acceptMethod(item.getAcptMthd())
                            .workPlace(item.getWorkPlcNm())
                            .jobClass(item.getJobclsNm())
                            .jobId(item.getJobId())
                            .deadline(item.getDeadline())
                            .employShape(changeShape(item.getEmplymShp()))
                            .startDate(changeDate(item.getFrDd()))
                            .endDate(changeDate(item.getToDd()))
                            .title(item.getRecrtTitle())
                            .build()).collect(Collectors.toList());

            employRepository.saveAll(employmentList);
        }
    }

    @Override
    @Scheduled (cron = "0 0 7 * * ?", zone = "Asia/Seoul")
    @Transactional
    public void deleteWorkList() {
        employRepository.deleteAll();
    }

    @Override
    public JobListResponeDto getJobList(JobListSearchRequestDto jobListSearchRequestDto) {
        Pageable pageable = PageRequest.of(jobListSearchRequestDto.getPageNum(), 16, Sort.by("endDate").ascending());

        JobListResponeDto jobListResponeDto;

        // 키워드 있음
        if (jobListSearchRequestDto.getKeyword() != "") {
            log.info("키워드 있음, 지역 검색어 : {}", jobListSearchRequestDto.getWorkPlcNm());
            
            Long total = employRepository.findAllByTitleContainingAndWorkPlaceLike(jobListSearchRequestDto.getKeyword(),
                    jobListSearchRequestDto.getWorkPlcNm()+"%").stream().count();

            List<JobListResponeDto.Item> items = employRepository.findAllByTitleContainingAndWorkPlaceLike(jobListSearchRequestDto.getKeyword(),
                            jobListSearchRequestDto.getWorkPlcNm()+"%", pageable).stream()
                    .map(job -> JobListResponeDto.Item.builder()
                            .acceptMethod(job.getAcceptMethod())
                            .jobClass(job.getJobClass())
                            .startDate(job.getStartDate())
                            .title(job.getTitle())
                            .workPlace(job.getWorkPlace())
                            .endDate(job.getEndDate())
                            .jobId(job.getJobId())
                            .employShape(job.getEmployShape())
                            .deadline(job.getDeadline())

                            .build())
                    .collect(Collectors.toList());

            jobListResponeDto = JobListResponeDto.builder()
                    .page(jobListSearchRequestDto.getPageNum() + 1)
                    .totalPage(getTotalPage(total))
                    .items(items)
                    .build();

        // 키워드 없음
        } else if (jobListSearchRequestDto.getWorkPlcNm() != "") {
            log.info("키워드 없음, 지역 검색어 : {}", jobListSearchRequestDto.getWorkPlcNm());
            Long total = employRepository.findAllByWorkPlaceLike(jobListSearchRequestDto.getWorkPlcNm()+"%").stream().count();

            List<JobListResponeDto.Item> items = employRepository.findAllByWorkPlaceLike(jobListSearchRequestDto.getWorkPlcNm()+"%", pageable).stream()
                    .map(job -> JobListResponeDto.Item.builder()
                            .acceptMethod(job.getAcceptMethod())
                            .jobClass(job.getJobClass())
                            .startDate(job.getStartDate())
                            .title(job.getTitle())
                            .workPlace(job.getWorkPlace())
                            .endDate(job.getEndDate())
                            .jobId(job.getJobId())
                            .employShape(job.getEmployShape())
                            .deadline(job.getDeadline())
                            .build())
                    .collect(Collectors.toList());

            jobListResponeDto = JobListResponeDto.builder()
                    .page(jobListSearchRequestDto.getPageNum() + 1)
                    .totalPage(getTotalPage(total))
                    .items(items)
                    .build();
        } else {
            log.info("키워드 없음, 지역 없음");
            Long total = employRepository.findAll().stream().count();

            List<JobListResponeDto.Item> items = employRepository.findAll(pageable).stream()
                    .map(job -> JobListResponeDto.Item.builder()
                            .acceptMethod(job.getAcceptMethod())
                            .jobClass(job.getJobClass())
                            .startDate(job.getStartDate())
                            .title(job.getTitle())
                            .workPlace(job.getWorkPlace())
                            .endDate(job.getEndDate())
                            .jobId(job.getJobId())
                            .employShape(job.getEmployShape())
                            .deadline(job.getDeadline())
                            .build())
                    .collect(Collectors.toList());

            jobListResponeDto = JobListResponeDto.builder()
                    .page(jobListSearchRequestDto.getPageNum()+1)
                    .totalPage(getTotalPage(total))
                    .items(items)
                    .build();
        }
        
        return jobListResponeDto;
    }

    // 일자리 목록 데이터 불러오기
    @Override
    public String getListData(int pageNo) {

        // 기본 검색
        return webClient.mutate()
                .baseUrl("http://apis.data.go.kr/B552474/SenuriService/getJobList")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", apiKey)
                        .queryParam("numOfRows", 500)
                        .queryParam("pageNo", pageNo)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // 상세 정보 페이지
    @Override
    public String getDetailData(String jobId) {

        return webClient.mutate()
                .baseUrl("http://apis.data.go.kr/B552474/SenuriService/getJobInfo")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", apiKey)
                        .queryParam("id", jobId)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private JSONObject xmlToJson(String xml) {
        JSONObject jsonObject = XML.toJSONObject(xml);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        JSONObject body = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");

        log.info("xml, JsonParsing 끝");
        return body;
    }
    
    // 전체 페이지 수 구하기
    private int getTotalPage(long totalCount) {
        int totalPage = 0;
        totalPage = (int) (totalCount / 16);
        if (totalCount % 16 != 0) {
            totalPage++;
        }
        return totalPage;
    }

    private void updateJobDetail(JobDetailResponseDto jobDetailResponseDto) {
        String acpt = jobDetailResponseDto.getAcptMthdCd();

        if (acpt.equals("CM0801")) {
            acpt = "온라인";
        } else if (acpt.equals("CM0802")) {
            acpt = "이메일";
        } else if (acpt.equals("CM0803")) {
            acpt = "팩스";
        } else if (acpt.equals("CM0804")){
            acpt = "방문";
        }

        jobDetailResponseDto.updateAcptMthdCd(acpt);
        jobDetailResponseDto.getFrAcptDd().insert(4, "-").insert(7,"-");
        jobDetailResponseDto.getToAcptDd().insert(4, "-").insert(7,"-");
    }

    private String changeShape(String acpt) {
        if (acpt.equals("CM0101")) {
            acpt = "정규직";
        } else if (acpt.equals("CM0102")) {
            acpt = "계약직";
        } else if (acpt.equals("CM0103")) {
            acpt = "시간제일자리";
        } else if (acpt.equals("CM0104")){
            acpt = "일당직";
        } else {
            acpt = "기타";
        }

        return acpt;
    }

    private LocalDate changeDate(StringBuilder Date) {
        LocalDate localDate = LocalDate.parse(Date.insert(4, "-").insert(7,"-"));
        return localDate;
    }

    private String imageSearch(String keyword) {
        LoadImageData loadImageData = webClient.mutate()
                .baseUrl("https://dapi.kakao.com/v2/search/image")
                .defaultHeader("Authorization", "KakaoAK " + REST_API_KEY)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", keyword)
                        .queryParam("page", 1)
                        .queryParam("size", 1)
                        .build())
                .retrieve()
                .bodyToMono(LoadImageData.class)
                .block();

        String image = loadImageData.getDocuments().get(0).getImage_url();

        return image;
    }
}
