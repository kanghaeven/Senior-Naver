package com.ssafy.seniornaver.auth.controller;

import com.ssafy.seniornaver.auth.dto.OAuth.OAuthSignInResponse;
import com.ssafy.seniornaver.auth.dto.OAuth.TokenRequest;
import com.ssafy.seniornaver.auth.service.OAuth.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService OAuthService;

    @GetMapping("/login/oauth2/code/{registrationId}")
    @Operation(summary = "네이버 로그인")
    public ResponseEntity<OAuthSignInResponse> redirect(
            @PathVariable("registrationId") String registrationId
            , @RequestParam("code") String code
            , @RequestParam("state") String state) {
        return ResponseEntity.ok(
                OAuthService.redirect(
                    TokenRequest.builder()
                            .registrationId(registrationId)
                            .code(code)
                            .state(state)
                            .build()));
    }
    // accessToken 생성 컨트롤러
    @PostMapping("/token")
    @Operation(summary = "네이버 토큰 재발급", description = "토큰을 재발급합니다. \n 토큰 앞에 항상 'Bearer '를 붙여주세요!")
    public ResponseEntity<OAuthSignInResponse> refreshToken(@RequestBody TokenRequest tokenRequest){
        return ResponseEntity.ok(OAuthService.refreshToken(tokenRequest));
    }
}
