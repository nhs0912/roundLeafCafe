package com.ypdchurch.roundleafcafe.common.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public JwtAuthFilter(ObjectMapper objectMapper) {
        super("/api/member/signin");
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        EmailPassword emailPassword = objectMapper.readValue(request.getInputStream(), EmailPassword.class);
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                emailPassword.getEmail(),
                emailPassword.getPassword()
        );
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }
}
