package com.ssafy.seniornaver.location.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchResponseDto {

    private Meta meta;
    private List<Document> documents;

    @Getter
    public  static class Meta {
        private int totalPage;
        private Integer pageable_count;

        public void updateTotalPage(int total) {
            this.totalPage = total;
        }
    }
    @Getter
    public static class Document {
        private String place_name;
        private String place_url;
        private String category_name;
        private String thumbnail;
        private String address_name;
        private String id;
        private String category_group_name;
        private String x;
        private String y;

        public void updateCategory(String categoryName) {
            this.category_name = categoryName;
        }
        public void updatePlaceName(String placeName) {
            this.place_name = placeName;
        }
        public void updateThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
