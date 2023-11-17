package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.curation.dto.PerformanceDetailListDto;
import com.ssafy.seniornaver.curation.dto.PerformanceDto;
import com.ssafy.seniornaver.curation.dto.PerformanceList;
import com.ssafy.seniornaver.curation.entity.Performance;
import com.ssafy.seniornaver.curation.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService{

    @Value("${kopis-api.serviceKey}")
    private String serviceKey;

    private final PerformanceRepository performanceRepository;
    private final WebClient webClient;

    @Scheduled(cron = "0 0 * * * *") // 초 분 시 일 월 주 = 매 시간마다 실행
    public List<Performance> savePerformanceList() throws Exception {
        // 기존 Performance 객체들을 모두 삭제
        performanceRepository.deleteAll();

        // 요청시간 기준 7일 이후 공연목록 저장
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        String stdate = startDate.format(formatter);
        String eddate = endDate.format(formatter);

        String apiURL = "http://www.kopis.or.kr/openApi/restful/pblprfr?service=" + serviceKey + "&stdate=" + stdate + "&eddate=" + eddate + "&cpage=1&rows=1000";
        // 테스팅용 1030 ~ 1120까지 1000개 데이터 조회 url
        // String apiURL = "http://www.kopis.or.kr/openApi/restful/pblprfr?service=" + serviceKey + "&stdate=20231030&eddate=20231120&cpage=1&rows=1000";

        JAXBContext jaxbContext = JAXBContext.newInstance(PerformanceList.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        PerformanceList performanceList = (PerformanceList) unmarshaller.unmarshal(new URL(apiURL));

        // Performance 객체들을 DB에 저장
        performanceRepository.saveAll(performanceList.getPerformances());

        return performanceList.getPerformances();
    }

    public Mono<PerformanceDetailListDto> getPerformanceDetail(String pfid) {
        String apiURL = "http://www.kopis.or.kr/openApi/restful/pblprfr/" + pfid + "?service=" + serviceKey;

        return webClient.get()
                .uri(apiURL)
                .retrieve()
                .bodyToMono(String.class) // WebClient를 사용하여 응답을 String으로 받아옵니다.
                .map(xmlString -> { // 받아온 XML String을 JAXB를 사용하여 객체로 변환합니다.
                    try {
                        JAXBContext jaxbContext = JAXBContext.newInstance(PerformanceDetailListDto.class);
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        StringReader reader = new StringReader(xmlString);
                        return (PerformanceDetailListDto) unmarshaller.unmarshal(reader);
                    } catch (JAXBException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public List<PerformanceDto> getPerformancesNearDate() {
        LocalDate date = LocalDate.now();
        List<Performance> performances = performanceRepository.findAll();

        performances.sort(Comparator.comparing(performance ->
                Math.abs(ChronoUnit.DAYS.between(date, performance.getStartDate()))));

        List<PerformanceDto> dtoList = performances.stream()
                .map(performance -> new PerformanceDto(
                        performance.getPfId(),
                        performance.getPfName(),
                        performance.getStartDate(),
                        performance.getEndDate(),
                        performance.getTheater(),
                        performance.getPoster(),
                        performance.getGenre(),
                        performance.getPfState(),
                        performance.getOpenRun()))
                .collect(Collectors.toList());

        return dtoList;
    }

}
