package com.ssafy.seniornaver.auth.jwt;

import com.ssafy.seniornaver.auth.dto.TokenDto;
import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {
    private final MemberRepository memberRepository;
    @Value("${jwt.secret.key}") private String secret;
    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60L; // 1 hours 1000 * 60 * 60L
//    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 30L; // 30 seconds

    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L; // 30 days

    public TokenDto createAccessToken(String memberId, AuthProvider provider) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("memberId", memberId);
        claim.put("provider", provider);
        return createJwt("ACCESS_TOKEN", ACCESS_TOKEN_EXPIRATION_TIME, claim);
    }

    public TokenDto createRefreshToken(String memberId, AuthProvider provider) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("memberId", memberId);
        claim.put("provider", provider);
        return createJwt("REFRESH_TOKEN", REFRESH_TOKEN_EXPIRATION_TIME, claim);
    }

    public TokenDto createJwt(String subject, Long expiration, HashMap<String, Object> claim) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret);
        // claim 없는 경우 추가
        if (claim != null) {
            jwtBuilder.setClaims(claim);
        }
        // 기간
        Date date = null;
        if (expiration != null) {
            date = new Date(new Date().getTime() + expiration);
            jwtBuilder.setExpiration(date);
        }
        String token = jwtBuilder.compact();
        return new TokenDto(token, date);
    }

    /**
     * 복호화
     */
    public Claims get(String jwt) throws JwtException {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
        } catch (JwtException e) {
            // 로그 추가
            log.error("Error parsing jwt: ", e);
            throw e;
        }
    }

    /**
     * 토큰 만료 여부 체크
     *
     * @return true : 만료됨, false : 만료되지 않음
     */
    public boolean isExpiration(String jwt) throws JwtException {
        try {
            return get(jwt).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("Token expired: ", e);
            return true;
        }
    }

}
