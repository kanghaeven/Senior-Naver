package com.ssafy.seniornaver.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.CustomAuthenticationException;
import com.ssafy.seniornaver.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

// CustomAuthenticationException 처리
        if (authException instanceof CustomAuthenticationException) {
            CustomAuthenticationException e = (CustomAuthenticationException) authException;

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
        }

        // 기타 AuthenticationException 처리
        if (authException != null) {
            ErrorCode errorCode = ErrorCode.NOT_VALID_TOKEN;
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
        }
    }
    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println("Bearer" + " : " + errorCode);
    }
}
