package com.ypdchurch.roundleafcafe.token.service;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.common.exception.TokenCustomException;
import com.ypdchurch.roundleafcafe.common.exception.code.TokenErrorCode;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.token.domain.AuthenticationTokens;
import com.ypdchurch.roundleafcafe.token.domain.Token;
import com.ypdchurch.roundleafcafe.token.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    private static final int ONE_HOUR = 3600;
    private static final String secretKey = "shgmltjrWKdwlSWKwhgdk123gkgkgkgkgk45324";
    @InjectMocks
    private TokenService tokenService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private JwtProvider jwtProvider;

    @BeforeEach
    void before() {
        jwtProvider = new JwtProvider(secretKey, 100, 100);
    }

    @Test
    @DisplayName("Token 발급 서비스 저장 성공")
    public void registerSuccessTest() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("tom@gmail.com")
                .build();
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), ONE_HOUR);
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail(), ONE_HOUR);
        //when
        when(tokenRepository.save(any())).thenReturn(Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .email("tom@gmail.com")
                .build());

        AuthenticationTokens tokens = AuthenticationTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        Token registeredRefreshToken = tokenService.registerRefreshToken(tokens, member);
        //then
        assertThat(registeredRefreshToken.getEmail()).isEqualTo("tom@gmail.com");
    }

    @Test
    @DisplayName("Token 삭제 memberId 서비스 저장 성공")
    public void deleteByMemberIdSuccessTest() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("tom@gmail.com")
                .build();
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), ONE_HOUR);
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail(), ONE_HOUR);
        Token token = Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .memberId(member.getId())
                .email("tom@gmail.com")
                .build();
        //when
        when(tokenRepository.findByMemberId(any()))
                .thenReturn(Optional.ofNullable(token));

        Optional<Long> memberIdOptional = tokenService.deleteByMemberId(member.getId());

        //then
        assertThat(memberIdOptional.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Token 삭제 email 서비스 저장 성공")
    public void deleteByEmailSuccessTest() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("tom@gmail.com")
                .build();
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), ONE_HOUR);
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail(), ONE_HOUR);
        Token token = Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .memberId(member.getId())
                .email("tom@gmail.com")
                .build();
        //when
        when(tokenRepository.findByEmail(any()))
                .thenReturn(Optional.ofNullable(token));

        Optional<Long> memberIdOptional = tokenService.deleteByEmail(member.getEmail());
        //then
        assertThat(memberIdOptional.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Access 토큰 유효시간 지난 토큰 검증 ")
    public void validAccessTokenOverTime() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("tom@gmail.com")
                .build();
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), -10);
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail(), -10);
        Token token = Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .memberId(member.getId())
                .email("tom@gmail.com")
                .build();

        assertThatThrownBy(() -> {
            token.isValidAccessToken(secretKey);
        }).isInstanceOf(TokenCustomException.class)
                .hasMessage(TokenErrorCode.TOKEN_IS_EXPIRED.getMessage());
    }

    @Test
    @DisplayName("Refresh 토큰 유효시간 지난 토큰 검증 ")
    public void validRefreshTokenOverTime() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("tom@gmail.com")
                .build();
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), -10);
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail(), -10);
        Token token = Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .memberId(member.getId())
                .email("tom@gmail.com")
                .build();

        assertThatThrownBy(() -> {
            token.isValidRefreshToken(secretKey);
        }).isInstanceOf(TokenCustomException.class)
                .hasMessage(TokenErrorCode.TOKEN_IS_EXPIRED.getMessage());
    }
}
