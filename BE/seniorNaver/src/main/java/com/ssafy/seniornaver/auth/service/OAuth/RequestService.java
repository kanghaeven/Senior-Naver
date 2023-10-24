package com.ssafy.seniornaver.auth.service.OAuth;

import com.ssafy.seniornaver.auth.dto.OAuth.OAuthSignInResponse;
import com.ssafy.seniornaver.auth.dto.OAuth.TokenRequest;
import com.ssafy.seniornaver.auth.dto.OAuth.TokenResponse;

public interface RequestService<T> {
    OAuthSignInResponse redirect(TokenRequest tokenRequest);
    TokenResponse getToken(TokenRequest tokenRequest);
    T getUserInfo(String accessToken);
    TokenResponse getRefreshToken(String provider, String refreshToken);
}
