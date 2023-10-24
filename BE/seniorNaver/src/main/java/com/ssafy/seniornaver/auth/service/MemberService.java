package com.ssafy.seniornaver.auth.service;

import com.ssafy.seniornaver.auth.dto.LogInRequestDto;
import com.ssafy.seniornaver.auth.dto.LogInResponseDto;
import com.ssafy.seniornaver.auth.dto.LogOutRequestDto;
import com.ssafy.seniornaver.auth.dto.SignUpRequestDto;

public interface MemberService {
    String signUp(SignUpRequestDto signUpRequestDto);

    LogInResponseDto logIn(LogInRequestDto logInRequestDto);

    Boolean validUserId(String userId);

    Boolean validNickname(String nickname);

    void logOut(LogOutRequestDto logOutRequestDto);
}
