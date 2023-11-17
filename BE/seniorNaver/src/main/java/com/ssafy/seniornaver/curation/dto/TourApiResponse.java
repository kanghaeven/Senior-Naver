package com.ssafy.seniornaver.curation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TourApiResponse {
    private Response response;

    public static class Response {
        private Header header;
        @Getter
        @Setter
        private Body body;

        public static class Header {
            private String resultCode;

            private String resultMsg;

        }

        public static class Body {
            @Getter
            @Setter
            private Items items;

            public static class Items {
                @Getter
                @Setter
                private List<TourDtDto> item;

            }
        }
    }
}
