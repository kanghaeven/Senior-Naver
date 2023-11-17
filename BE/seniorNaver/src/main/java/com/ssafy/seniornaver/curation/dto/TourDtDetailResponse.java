package com.ssafy.seniornaver.curation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TourDtDetailResponse {
    private Response response;
    // getters and setters

    public static class Response {
        private Header header;
        @Getter
        @Setter
        private Body body;
        // getters and setters

        public static class Header {
            private String resultCode;
            private String resultMsg;
            // getters and setters
        }

        public static class Body {
            @Getter
            @Setter
            private Items items;
            // getters and setters

            public static class Items {
                @Getter
                @Setter
                private List<TourDtDetail> item;
                // getters and setters
            }
        }
    }
}

