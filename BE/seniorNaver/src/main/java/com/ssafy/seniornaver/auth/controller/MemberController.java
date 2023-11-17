package com.ssafy.seniornaver.auth.controller;

import com.ssafy.seniornaver.auth.dto.Request.*;
import com.ssafy.seniornaver.auth.dto.Response.LogInResponseDto;
import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.auth.service.MemberService;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            String errorMessage = allErrors.get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        return ResponseEntity.ok(memberService.signUp(signUpRequestDto));
    }

    @PostMapping(value = "/details", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "지역, 닉네임, 키워드, 프로필 사진 추가")
    private ResponseEntity<String> addDetails(@Valid @RequestPart(value="keywordRequestDto") DetailRequestDto DetailRequestDto,
                                                @RequestPart(value = "file", required = false) MultipartFile file,
                                                BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            String errorMessage = allErrors.get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        return ResponseEntity.ok(memberService.addDetails(DetailRequestDto,file));
    }

    // 일반 유저 로그인
    @PostMapping("/login")
    @Operation(summary = "일반 로그인")
    public ResponseEntity<LogInResponseDto> logIn(@RequestBody LogInRequestDto logInRequestDto){
        return ResponseEntity.ok(memberService.logIn(logInRequestDto));
    }

    /*
     ** 로그아웃
     ** 프론트에서 access, refresh token을 전달받고 로그아웃 처리
     ** 프론트에서도 store에 저장된 token 정보를 삭제
     */
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", security = @SecurityRequirement(name = "Bearer"))
    public String logOut(HttpServletRequest request) {
        Member member = getMember(request);
        memberService.logOut(member.getMemberId());
        return "success logout";
    }

    @DeleteMapping("/deleteUser")
    @Operation(summary = "회원탈퇴", security = @SecurityRequirement(name = "Bearer"))
    public String deleteUser(HttpServletRequest request) {
        Member member = getMember(request);
        memberService.deleteUser(member.getMemberId());
        return "success delete";
    }
    // 유저 아이디 중복체크
    @PostMapping("/valid/memberId")
    @Operation(summary = "아이디 중복체크")
    public ResponseEntity<Boolean> validUserId(@RequestParam String memberId) {
        return ResponseEntity.ok(memberService.validMemberId(memberId));
    }

    // 유저 닉네임 중복체크
    @PostMapping("/valid/nickname")
    @Operation(summary = "닉네임 중복체크")
    public ResponseEntity<Boolean> validNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(memberService.validNickname(nickname));
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
