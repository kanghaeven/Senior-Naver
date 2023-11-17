package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.curation.dto.NewsScrapDto;
import com.ssafy.seniornaver.curation.dto.PFScrapDto;
import com.ssafy.seniornaver.curation.dto.TourScrapDto;
import com.ssafy.seniornaver.curation.entity.NewsScrap;
import com.ssafy.seniornaver.curation.entity.PFScrap;
import com.ssafy.seniornaver.curation.entity.TourScrap;
import com.ssafy.seniornaver.curation.repository.NewsScrapRepository;
import com.ssafy.seniornaver.curation.repository.PFScrapRepository;
import com.ssafy.seniornaver.curation.repository.TourScrapRepository;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService{
    private final NewsScrapRepository newsScrapRepository;
    private final PFScrapRepository pfScrapRepository;
    private final TourScrapRepository tourScrapRepository;

    @Override
    public NewsScrapDto scrapNews(NewsScrapDto newsScrapDto) {
        Optional<NewsScrap> existingScrap = newsScrapRepository.findByMemberIdAndTitle(
                newsScrapDto.getMemberId(), newsScrapDto.getTitle());
        if (existingScrap.isPresent()) {
            throw new BadRequestException(ErrorCode.DUPLICATED_SCRAP);
        }
        NewsScrap newsScrap = NewsScrap.builder()
                .memberId(newsScrapDto.getMemberId())
                .title(newsScrapDto.getTitle())
                .link(newsScrapDto.getLink())
                .imageUrl(newsScrapDto.getImageUrl())
                .build();
        newsScrapRepository.save(newsScrap);
        return newsScrapDto;
    }

    @Override
    public PFScrapDto scrapPF(PFScrapDto pfScrapDto) {
        Optional<PFScrap> existingScrap = pfScrapRepository.findByMemberIdAndPfId(
                pfScrapDto.getMemberId(), pfScrapDto.getPfId());
        if (existingScrap.isPresent()) {
            throw new BadRequestException(ErrorCode.DUPLICATED_SCRAP);
        }
        PFScrap pfScrap = PFScrap.builder()
                .memberId(pfScrapDto.getMemberId())
                .pfId(pfScrapDto.getPfId())
                .pfName(pfScrapDto.getPfName())
                .poster(pfScrapDto.getPoster())
                .build();
        pfScrapRepository.save(pfScrap);
        return pfScrapDto;
    }

    @Override
    public TourScrapDto scrapTour(TourScrapDto tourScrapDto) {
        Optional<TourScrap> existingScrap = tourScrapRepository.findByMemberIdAndTitle(
                tourScrapDto.getMemberId(), tourScrapDto.getTitle());
        if (existingScrap.isPresent()) {
            throw new BadRequestException(ErrorCode.DUPLICATED_SCRAP);
        }
        TourScrap tourScrap = TourScrap.builder()
                .memberId(tourScrapDto.getMemberId())
                .title(tourScrapDto.getTitle())
                .firstImage(tourScrapDto.getFirstImage())
                .contentId(tourScrapDto.getContentId())
                .build();
        tourScrapRepository.save(tourScrap);
        return tourScrapDto;
    }

    @Override
    public List<NewsScrapDto> getNewsScrap(String memberId) {
        List<NewsScrap> newsScraps = newsScrapRepository.findByMemberId(memberId);
        return newsScraps.stream()
                .map(newsScrap -> NewsScrapDto.builder()
                        .memberId(newsScrap.getMemberId())
                        .title(newsScrap.getTitle())
                        .link(newsScrap.getLink())
                        .imageUrl(newsScrap.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<PFScrapDto> getPFScrap(String memberId) {
        List<PFScrap> pfScraps = pfScrapRepository.findByMemberId(memberId);
        return pfScraps.stream()
                .map(pfScrap -> PFScrapDto.builder()
                        .memberId(pfScrap.getMemberId())
                        .pfId(pfScrap.getPfId())
                        .pfName(pfScrap.getPfName())
                        .poster(pfScrap.getPoster())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TourScrapDto> getTourScrap(String memberId) {
        List<TourScrap> tourScraps = tourScrapRepository.findByMemberId(memberId);
        return tourScraps.stream()
                .map(tourScrap -> TourScrapDto.builder()
                        .memberId(tourScrap.getMemberId())
                        .title(tourScrap.getTitle())
                        .firstImage(tourScrap.getFirstImage())
                        .contentId(tourScrap.getContentId())
                        .build())
                .collect(Collectors.toList());
    }
}
