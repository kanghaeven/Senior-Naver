package com.ssafy.seniornaver.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import com.ssafy.seniornaver.error.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, BadRequestException {

        try {
            log.info("filter 도착");
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            String memberId = null;
            String provider = null;
            log.info("authorizationHeader" + authorizationHeader);

            if (request.getServletPath().startsWith("/auth")
                || request.getServletPath().startsWith("/oauth/login/oauth2")
                || request.getServletPath().startsWith("/**/v1/**")) {
                log.info(request.getServletPath());
                filterChain.doFilter(request, response);
                return;
            }
            log.info("auth 지나침");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                log.info("Bearer check");
                token = authorizationHeader.substring(7);

                log.info("Checking token expiration");
                if (jwtProvider.isExpiration(token)) { // 만료되었는지 체크
                    throw new BadRequestException(ErrorCode.TOKEN_EXPIRED);
                }
                log.info("Token is valid");

                memberId = (String) jwtProvider.get(token).get("memberId");
                log.info("Extracted memberId from token: " + memberId);

                provider = (String) jwtProvider.get(token).get("provider");

                log.info("Checking if member exists");
                if(!memberRepository.existsByMemberId(memberId)){
                    throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID);
                }
                // 인증 정보 등록 및 다음 체인으로 이동
                log.info("Security filter에 access Token 저장  " + token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        memberId, null, List.of(new SimpleGrantedAuthority("USER")));
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            log.info("Bearer 지나침");
            filterChain.doFilter(request, response);
        } catch (BadRequestException e) {
            log.info("BadRequest");

            // 에러 메시지를 ErrorResponse로 변환
            ErrorCode errorCode = e.getErrorCode();
            ErrorResponse errorResponse = ErrorResponse.of(
                    errorCode.getHttpStatus().toString(),
                    errorCode.getErrorCode(),
                    errorCode.getMessage()
            );
            response.setStatus(errorCode.getHttpStatus().value());
            response.setContentType("application/json");

            // 에러 메시지를 JSON으로 변환
            ObjectMapper mapper = new ObjectMapper();
            String jsonErrorResponse = mapper.writeValueAsString(errorResponse);

            // 에러 메시지를 응답에 작성
            PrintWriter out = response.getWriter();
            out.print(jsonErrorResponse);

            return;
        } finally {
            log.debug("**** SECURITY FILTER FINISH");
        }

    }

}
