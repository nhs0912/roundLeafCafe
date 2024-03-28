package com.ypdchurch.roundleafcafe.common.auth.jwt.filter;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.common.exception.TokenCustomException;
import com.ypdchurch.roundleafcafe.common.exception.code.TokenErrorCode;
import com.ypdchurch.roundleafcafe.token.domain.Token;
import com.ypdchurch.roundleafcafe.token.service.TokenService;
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
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("headerToken = {} ", accessTokenHeader);
        Optional<String> accessTokenOptional = findToken(accessTokenHeader);

        if (accessTokenOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("accessTokenOptional token = {} ", accessTokenOptional.get());

        String email = jwtProvider.findEmail(accessTokenOptional.get());
        log.info("accessTokenOptional email = {}", email);
        String secretKey = jwtProvider.getSecretKey();
        Token foundAccessToken = tokenService.findByEmail(email)
                .orElseThrow(() -> new TokenCustomException(TokenErrorCode.TOKEN_IS_NOT_FOUND));

        if (!foundAccessToken.isValidAccessToken(secretKey) && foundAccessToken.isValidRefreshToken(secretKey)) {
            Token newToken = makeNewToken(foundAccessToken, email);
            Authentication authentication = jwtProvider.getAuthentication(newToken.getAccessToken());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("AuthorizationFilter doFilterInternal authentication = {}", authentication);
            filterChain.doFilter(request, response);
        }

        if (!foundAccessToken.isValidAccessToken(secretKey) && !foundAccessToken.isValidRefreshToken(secretKey)) {
            log.info("두개의 토큰 유효성 불일치 로그인 페이지 이동");
            response.sendRedirect("api/member/signin");
            throw new TokenCustomException(TokenErrorCode.NEED_TO_LOGIN_AGAIN);
        }
    }

    private Token makeNewToken(Token foundAccessToken, String email) {
        String newRefreshTokenText = jwtProvider.createRefreshToken(email, jwtProvider.getRefreshValidTime());
        String newAccessTokenText = jwtProvider.createAccessToken(email, jwtProvider.getAccessValidTime());
        Token updateRefreshToken = foundAccessToken.updateRefreshToken(newRefreshTokenText);
        return updateRefreshToken.updateAccessToken(newAccessTokenText);
    }

    private Optional<String> findToken(String tokenHeader) {
        if (StringUtils.isEmpty(tokenHeader)) {
            return Optional.empty();
        }
        return Optional.of(tokenHeader.substring(JwtProvider.TOKEN_PREFIX.length()));
    }
}
