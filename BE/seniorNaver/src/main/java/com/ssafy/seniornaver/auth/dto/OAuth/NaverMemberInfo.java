package com.ssafy.seniornaver.auth.dto.OAuth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverMemberInfo {
    private String resultcode;
    private String message;
    private NaverAccount response;

    @Getter
    public static class NaverAccount {
        private String id;
        private String nickname;
        private String name;
        private String email;
        private String gender;
        private String age;
        private String profile_image;
        private String mobile;
    }
}
