package com.ssafy.seniornaver.auth.dto;

import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import lombok.Getter;

@Getter
public class SignUpRequest {
    private String memberId;
    private String email;
    private String nickname;
    private AuthProvider authProvider;
}
