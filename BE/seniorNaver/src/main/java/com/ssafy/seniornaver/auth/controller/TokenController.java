package com.ssafy.seniornaver.auth.controller;

import com.ssafy.seniornaver.auth.dto.TokenDto;
import com.ssafy.seniornaver.auth.service.MemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final MemberServiceImpl memberService;

    // AccessToken 재발급
    @PostMapping("/reAccess")
    @Operation(summary = "일반 토큰 재발급", description = "토큰을 재발급합니다. \n 토큰 앞에 항상 'Bearer '를 붙여주세요!", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<TokenDto> getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        return ResponseEntity.ok(memberService.getAccessToken(token));
    }
}
