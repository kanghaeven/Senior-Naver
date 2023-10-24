package com.ssafy.seniornaver.util;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class UserTokenUtil {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public Member getUser(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization").substring(7);
        String userId = (String) jwtProvider.get(bearer).get("userId");

        Member member = memberRepository.findByMemberId(userId).orElseThrow(() -> {
            return new IllegalArgumentException("유저 ID를 찾을수 없습니다.");
        });
        return member;
    }
}
