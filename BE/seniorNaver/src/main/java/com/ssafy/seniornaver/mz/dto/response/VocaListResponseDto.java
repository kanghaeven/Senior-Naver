package com.ssafy.seniornaver.mz.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class VocaListResponseDto {

    private int page;
    private int totalPage;
    private List<Item> items;

    @Getter
    public static class Item {
        private Long id;
        private String title;
        private String content;
        private int year;

        @Builder
        public Item(Long id, String title, String content, int year) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.year = year;
        }
    }

    @Builder
    public VocaListResponseDto(int page, int totalPage, List<Item> items) {
        this.page = page;
        this.totalPage = totalPage;
        this.items = items;
    }

}
