package com.registration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.registration.ex.ErrorDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // Fixed: Now using all 4 parameters for ErrorDetails
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),                  // timestamp
                "Authentication failed",              // errorMessage
                request.getRequestURI(),              // details
                HttpStatus.UNAUTHORIZED.value()       // status
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, errorDetails);
        responseStream.flush();
    }
}