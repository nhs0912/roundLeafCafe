package com.ypdchurch.roundleafcafe.common.auth.jwt.filter;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.common.exception.TokenCustomException;
import com.ypdchurch.roundleafcafe.common.exception.code.TokenErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        String refreshTokenHeader = request.getHeader(JwtProvider.REFRESH_TOKEN_HEADER);

        if (!StringUtils.isEmpty(accessTokenHeader)) {
            String accessToken = findToken(accessTokenHeader);
//        String refreshToken = findToken(refreshTokenHeader);

            if (!jwtProvider.isValidToken(accessToken)) {
                throw new TokenCustomException(TokenErrorCode.INVALID_TOKEN);
            }

            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("AuthorizationFilter doFilterInternal authentication = {}", authentication);
            log.info("AuthorizationFilter doFilterInternal accessToken = {}", accessToken);
//        log.info("AuthorizationFilter doFilterInternal refreshToken = {}", refreshToken);
        }
        filterChain.doFilter(request, response);
    }

    private String findToken(String tokenHeader) {
        if (StringUtils.isEmpty(tokenHeader)) {
            throw new TokenCustomException(TokenErrorCode.TOKEN_IS_NOT_FOUND);
        }
        return tokenHeader.substring(JwtProvider.TOKEN_PREFIX.length());
    }
}
