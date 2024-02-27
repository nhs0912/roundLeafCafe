package com.ypdchurch.roundleafcafe.common.auth.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypdchurch.roundleafcafe.common.auth.jwt.EmailPassword;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public JwtAuthFilter(String url, ObjectMapper objectMapper) {
        super(url);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        log.info("request = {}", request.toString());
        EmailPassword emailPassword = objectMapper.readValue(request.getInputStream(), EmailPassword.class);
        log.info("EmailPassword = {}", emailPassword);

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                emailPassword.getEmail(),
                emailPassword.getPassword()
        );
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }
}
