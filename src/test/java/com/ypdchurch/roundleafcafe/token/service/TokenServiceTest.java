package com.ypdchurch.roundleafcafe.token.service;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.common.config.JwtConfig;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
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
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        final String secretKey = "testBase64TokenService";
        when(new JwtConfig().getSecretKey()).thenReturn(secretKey);
        jwtProvider = new JwtProvider(new JwtConfig());
        tokenService = new TokenService(tokenRepository, memberService, jwtProvider);
    }

    @Test
    @DisplayName("Token 발급 서비스 저장 성공")
    public void registerSuccessTest() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("tom@gmail.com")
                .build();
        Token refreshToken = jwtProvider.createRefreshToken(member);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        tokenService.registerRefreshToken(refreshToken.getRefreshToken());

        assertThat(refreshToken.getEmail()).isEqualTo("tom@gmail.com");
    }
}
