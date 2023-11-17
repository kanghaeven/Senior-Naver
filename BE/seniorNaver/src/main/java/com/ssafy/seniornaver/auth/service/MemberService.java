package com.ssafy.seniornaver.auth.service;

import com.ssafy.seniornaver.auth.dto.Request.*;
import com.ssafy.seniornaver.auth.dto.Response.LogInResponseDto;
import com.ssafy.seniornaver.auth.dto.Response.MemberResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    String signUp(SignUpRequestDto signUpRequestDto);

    LogInResponseDto logIn(LogInRequestDto logInRequestDto);

    Boolean validMemberId(String memberId);

    Boolean validNickname(String nickname);

    MemberResponseDto getMemberInfo(String memberId);

    String addDetails(DetailRequestDto DetailRequestDto, MultipartFile file) throws IOException;

    void logOut(String token);

    void deleteUser(String memberId);
}
