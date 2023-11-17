package com.ssafy.seniornaver.curation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoImageResponse {
    private List<KakaoImageDocument> documents;
}
