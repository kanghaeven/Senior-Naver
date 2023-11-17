package com.ssafy.seniornaver.chatbot.controller;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.auth.service.ProfileService;
import com.ssafy.seniornaver.chatbot.service.ChatbotService;
import com.ssafy.seniornaver.curation.service.ScrapService;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @PostMapping(value="/v1/talk",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "음성 텍스트 변형", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<byte[]> convertAndTalk(@RequestPart("voiceFile") MultipartFile voiceFile, HttpServletRequest request) {

        // 파일 이름 확인
        String originalFilename = voiceFile.getOriginalFilename();
        log.info("전송된 파일 이름: " + originalFilename);

        // 음성을 텍스트로 변환
        String text = chatbotService.convertSpeechToText(voiceFile);
        log.info("테스트2 " + text);
// 로그인 여부에 따라 챗봇에 전달하고 챗봇의 응답을 받음
        String authHeader = request.getHeader("Authorization");
        String response;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                // 로그인한 사용자
                Member member = getMember(request);
                response = chatbotService.talkToChat(text, member);
            } catch (BadRequestException e) {
                // 로그인하지 않았거나, 토큰이 만료되었거나, 사용자 정보를 찾을 수 없는 경우
                response = chatbotService.talkToChat(text, null);
            }
        } else {
            // 로그인하지 않은 사용자
            response = chatbotService.talkToChat(text, null);
        }
        log.info("테스트3 " + response);
        // 챗봇의 응답을 음성으로 변환
        byte[] voiceData = chatbotService.convertTextToSpeech(response);
        
        // 음성 데이터를 HTTP 응답 본문에 담아 반환
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .body(voiceData);
//        return response;
    }

    private Member getMember(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        String bearer = header.substring(7);

        String memberId;
        try {
            memberId = (String) jwtProvider.get(bearer).get("memberId");
        } catch (ExpiredJwtException e) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID);
        }

        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID);
        });
        return member;
    }
}
