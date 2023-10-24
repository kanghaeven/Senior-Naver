package com.ssafy.seniornaver.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String exception = (String) request.getAttribute("Authorization");
        String errorCode;

        if (exception.equals("토큰이 만료되었습니다.")) {
            errorCode = "토큰이 만료되었습니다.";
            final Map<String, Object> body = new HashMap<>();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            body.put("status", 403);
            body.put("error", "Expired");
            body.put("message", errorCode);
            body.put("path", request.getServletPath());
            final ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(response.getOutputStream(), body);
            response.setStatus(HttpServletResponse.SC_OK);
        }

        if (exception.equals("유효하지 않은 토큰입니다.")) {
            errorCode = "유효하지 않은 토큰입니다.";
            final Map<String, Object> body = new HashMap<>();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("error", "Unauthorized");
            body.put("message", errorCode);
            body.put("path", request.getServletPath());
            final ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(response.getOutputStream(), body);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println("Bearer" + " : " + errorCode);
    }
}
