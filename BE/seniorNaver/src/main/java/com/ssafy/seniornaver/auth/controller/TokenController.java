package com.ssafy.seniornaver.auth.controller;

import com.ssafy.seniornaver.auth.dto.TokenDto;
import com.ssafy.seniornaver.auth.service.MemberServiceImpl;
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
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        return ResponseEntity.ok(memberService.getAccessToken(token));
    }
}
