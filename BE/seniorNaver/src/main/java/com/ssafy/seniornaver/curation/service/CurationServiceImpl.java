package com.ssafy.seniornaver.curation.service;

import com.ssafy.seniornaver.curation.dto.MZWordDto;
import com.ssafy.seniornaver.mz.entity.Dictionary;
import com.ssafy.seniornaver.mz.repository.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService{
    private final DictionaryRepository dictionaryRepository;

    @Override
    public List<MZWordDto> getCarouselMzWords() {
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();

        return dictionaryList.stream()
                .limit(10)
                .map(MZWordDto::new)
                .collect(Collectors.toList());
    }

}
