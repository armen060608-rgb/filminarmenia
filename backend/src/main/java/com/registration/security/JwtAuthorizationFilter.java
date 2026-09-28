package com.registration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.registration.ex.ErrorDetails;
import com.registration.ex.userexceptions.UserBadRequestException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }
            Claims claims = jwtUtil.resolveClaims(request);

            if (claims != null && jwtUtil.validateClaims(claims)) {
                String email = claims.getSubject();

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email, "", new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            if (e instanceof UserBadRequestException) {
                // Fixed: Now using all 4 parameters for ErrorDetails
                ErrorDetails errorDetails = new ErrorDetails(
                        LocalDateTime.now(),              // timestamp
                        e.getMessage(),                   // errorMessage
                        request.getRequestURI(),          // details
                        HttpStatus.UNAUTHORIZED.value()   // status
                );

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                OutputStream responseStream = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(responseStream, errorDetails);
                responseStream.flush();
            }
        }
    }
}