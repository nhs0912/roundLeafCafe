package com.ypdchurch.roundleafcafe.token.service;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.common.config.MemberPrincipal;
import com.ypdchurch.roundleafcafe.common.exception.TokenCustomException;
import com.ypdchurch.roundleafcafe.common.exception.code.TokenErrorCode;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
import com.ypdchurch.roundleafcafe.token.domain.AuthenticationTokens;
import com.ypdchurch.roundleafcafe.token.domain.Token;
import com.ypdchurch.roundleafcafe.token.enums.TokenStatus;
import com.ypdchurch.roundleafcafe.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    public Optional<Token> findByRefreshToken(String token) {
        return tokenRepository.findByRefreshToken(token);
    }

    public Optional<Token> findByAccessToken(String token) {
        return tokenRepository.findByAccessToken(token);
    }

    public Optional<Token> findByEmail(String email) {
        return tokenRepository.findByEmail(email);
    }

    public Optional<Token> findByMemberId(Long memberId) {
        return tokenRepository.findByMemberId(memberId);
    }

    public Token registerRefreshToken(AuthenticationTokens token, Member member) {
        Token refreshToken = Token.builder()
                .refreshToken(token.getRefreshToken())
                .accessToken(token.getAccessToken())
                .memberId(member.getId())
                .email(member.getEmail())
                .status(TokenStatus.ACTIVE)
                .build();
        return tokenRepository.save(refreshToken);
    }

    public Token updateRefreshToken(Token token) {
        token.isValidRefreshToken();
        String newRefreshToken = jwtProvider.createRefreshToken(token.getEmail());
        return token.updateRefreshToken(newRefreshToken);
    }

    public AuthenticationTokens getAuthenticationTokens(String email) {
        String refreshToken = jwtProvider.createRefreshToken(email);
        String accessToken = jwtProvider.createAccessToken(email);
        return AuthenticationTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Optional<Long> deleteByRefreshToken(String token) {
        Optional<Token> refreshTokenOptional = findByRefreshToken(token);
        if (refreshTokenOptional.isEmpty()) {
            return Optional.empty();
        }
        Token registeredRefreshToken = refreshTokenOptional.get();
        tokenRepository.delete(registeredRefreshToken);
        return Optional.ofNullable(registeredRefreshToken.getId());
    }

    public Optional<Long> deleteByEmail(String email) {
        Optional<Token> refreshTokenOptional = findByEmail(email);
        return refreshTokenOptional.flatMap(this::deleteToken);
    }

    public Optional<Long> deleteByMemberId(Long memberId) {
        Optional<Token> refreshTokenOptional = findByMemberId(memberId);
        return refreshTokenOptional.flatMap(this::deleteToken);
    }

    private Optional<Long> deleteToken(Token token) {
        this.deleteByRefreshToken(token.getRefreshToken());
        return Optional.ofNullable(token.getMemberId());
    }

}
