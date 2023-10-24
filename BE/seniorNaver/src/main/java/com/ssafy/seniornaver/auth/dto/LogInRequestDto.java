package com.ssafy.seniornaver.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class LogInRequestDto {
    private String memberId;
    private String password;

}
