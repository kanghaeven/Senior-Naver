package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.auth.entity.Keyword;
import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.repository.KeywordRepository;
import com.ssafy.seniornaver.curation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService{

    @Value("${naver-news.clientId}")
    private String clientId;
    @Value("${naver-news.clientSecret}")
    private String clientSecret;
    @Value("${news-img.kakaoApi}")
    private String kakaoApi;

    private final WebClient webClient;
    private final KeywordRepository keywordRepository;

    public List<NewsDto> getNewsList(String keyword, int display) {
        String searchUrl = "https://openapi.naver.com/v1/search/news.json?query=" + keyword + "&display=" + display;

        SimpleDateFormat newsInputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        SimpleDateFormat newsOutputFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat imageInputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        List<NewsDto> newsList = this.webClient.get()
                .uri(searchUrl)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(NewsApiResponse.class)
                .map(newsApiResponse -> newsApiResponse.getItems())
                .block();

        if (newsList != null) {
            for (NewsDto news : newsList) {

                String articleTitle = news.getTitle().replaceAll("[^a-zA-Z0-9가-힣 ]", "");

                Date newsDate = null;
                try {
                    newsDate = newsInputFormat.parse(news.getPubDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String pubDate = newsOutputFormat.format(newsDate);

                String imageSearchUrl = "https://dapi.kakao.com/v2/search/image?query=" + articleTitle;

                List<KakaoImageDocument> imageList = this.webClient.get()
                        .uri(imageSearchUrl)
                        .header("Authorization", kakaoApi)
                        .retrieve()
                        .bodyToMono(KakaoImageResponse.class)
                        .map(kakaoImageResponse -> kakaoImageResponse.getDocuments())
                        .block();

                if (imageList != null) {
                    for (KakaoImageDocument image : imageList) {
                        String imageUrl = image.getImage_url();

                        Date imageDate = null;
                        try {
                            imageDate = imageInputFormat.parse(image.getDatetime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String imagePubDate = newsOutputFormat.format(imageDate);


                        if (imagePubDate.equals(pubDate)) {
                            news.setImageUrl(imageUrl);
                            break;
                        }
                    }
                }
            }
            newsList = newsList.stream()
                    .filter(news -> news.getImageUrl() != null)
                    .collect(Collectors.toList());

        }

        return newsList;
    }

    // display값을 받지않았을때의 기본값을
    // 메소드 오버로딩으로 처리합니다.
    public List<NewsDto> getNewsList(String keyword) {
        return getNewsList(keyword, 40);
    }

    public List<CurationDto> getCarouselNews(Member member) {
        String keyword;

        if (member == null) {
            keyword = "건강";  // 로그인하지 않은 사용자, 기본 키워드 사용
        } else {
            List<Keyword> keywordList = keywordRepository.findByMember(member);

            if (keywordList.isEmpty()) {
                keyword = "건강";  // 사용자가 가진 키워드가 없는 경우, 기본 키워드 사용
            } else {
                Random random = new Random();
                int index = random.nextInt(keywordList.size());
                keyword = keywordList.get(index).getKeyword();  // 랜덤으로 키워드 선택
            }
        }

        List<NewsDto> newsList = getNewsList(keyword, 20);

        if (newsList.size() > 10) {
            newsList = newsList.subList(0, 10);  // 캐러셀에 보여줄 뉴스의 수를 제한
        }

        // NewsDto를 CurationDto로 변환
        return newsList.stream()
                .map(CurationDto::new)
                .collect(Collectors.toList());
    }

}
