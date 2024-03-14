package com.ypdchurch.roundleafcafe.common.exception.handler;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.common.config.MemberPrincipal;
import com.ypdchurch.roundleafcafe.token.domain.AuthenticationTokens;
import com.ypdchurch.roundleafcafe.token.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        log.info("[인증성공] user={}, password = {}", principal.getUsername(), principal.getPassword());
        AuthenticationTokens tokens = tokenService.getAuthenticationTokens(principal.getUsername());
        tokenService.registerRefreshToken(tokens.getRefreshToken());

        setupLoginSuccessResponse(response, AuthenticationTokens.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .build());
    }

    private void setupLoginSuccessResponse(HttpServletResponse response, AuthenticationTokens tokens) {
        response.addHeader(HttpHeaders.AUTHORIZATION, JwtProvider.TOKEN_PREFIX + tokens.getAccessToken());
        response.addHeader("refreshToken", JwtProvider.TOKEN_PREFIX + tokens.getRefreshToken());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(SC_OK);
    }
}
