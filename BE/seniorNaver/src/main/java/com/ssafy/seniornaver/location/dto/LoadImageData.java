package com.ssafy.seniornaver.location.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LoadImageData {

    private List<Document> documents;

    @Getter
    public static class Document {
        private String thumbnail_url;
        private String image_url;
    }
}
