package com.ssafy.seniornaver.auth.controller;

import com.ssafy.seniornaver.auth.dto.LogInRequestDto;
import com.ssafy.seniornaver.auth.dto.LogInResponseDto;
import com.ssafy.seniornaver.auth.dto.LogOutRequestDto;
import com.ssafy.seniornaver.auth.dto.SignUpRequestDto;
import com.ssafy.seniornaver.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            String errorMessage = allErrors.get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        return ResponseEntity.ok(memberService.signUp(signUpRequestDto));
    }

    // 일반 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> logIn(@RequestBody LogInRequestDto logInRequestDto){
        return ResponseEntity.ok(memberService.logIn(logInRequestDto));
    }

    /*
     ** 로그아웃
     ** 프론트에서 access, refresh token을 전달받고 로그아웃 처리
     ** 프론트에서도 store에 저장된 token 정보를 삭제
     */
    @PostMapping("/logout")
    public String logOut(@RequestBody LogOutRequestDto logOutRequestDto) {
        memberService.logOut(logOutRequestDto);
        return "success logout";
    }
    // 유저 아이디 중복체크
    @PostMapping("/valid/userId")
    public ResponseEntity<Boolean> validUserId(@RequestParam String userId) {
        return ResponseEntity.ok(memberService.validUserId(userId));
    }
    // 유저 닉네임 중복체크
    @PostMapping("/valid/nickname")
    public ResponseEntity<Boolean> validNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(memberService.validNickname(nickname));
    }
}
