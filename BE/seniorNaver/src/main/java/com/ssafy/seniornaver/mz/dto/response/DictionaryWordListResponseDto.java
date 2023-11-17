package com.ssafy.seniornaver.mz.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DictionaryWordListResponseDto {

    private int page;
    private int totalPage;
    private List<Item> items;

    @Getter
    public static class Item {
        private Long wordId;
        private String word;
        private String mean;
        private int year;
        private boolean scrap;

        @Builder
        public Item(Long wordId, String word, String mean,
                                             int year, boolean scrap) {
            this.wordId = wordId;
            this.word = word;
            this.mean = mean;
            this.year = year;
            this.scrap = scrap;
        }
    }

    @Builder
    public DictionaryWordListResponseDto(int page, int totalPage, List<Item> items) {
        this.page = page;
        this.totalPage = totalPage;
        this.items = items;
    }
}
