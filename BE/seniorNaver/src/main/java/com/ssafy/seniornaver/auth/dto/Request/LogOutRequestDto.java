package com.ssafy.seniornaver.auth.dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogOutRequestDto {

    private String refreshToken;
}
