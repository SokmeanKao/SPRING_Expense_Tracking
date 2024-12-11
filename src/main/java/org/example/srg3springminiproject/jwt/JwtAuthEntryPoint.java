package org.example.srg3springminiproject.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        LocalDateTime currentTime = LocalDateTime.now();
        String errorMessage = "{ \"timestamp\": \"" + currentTime + "\", " +
                "\"status\": " + HttpServletResponse.SC_UNAUTHORIZED + ", " +
                "\"error\": \"Unauthorized\"}";
        PrintWriter writer = response.getWriter();
        writer.println(errorMessage);
    }
}
