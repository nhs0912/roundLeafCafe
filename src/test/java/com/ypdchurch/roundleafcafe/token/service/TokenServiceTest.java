package com.ypdchurch.roundleafcafe.token.service;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
import com.ypdchurch.roundleafcafe.token.domain.AuthenticationTokens;
import com.ypdchurch.roundleafcafe.token.domain.Token;
import com.ypdchurch.roundleafcafe.token.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("Token 발급 서비스 저장 성공")
    public void registerSuccessTest() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("tom@gmail.com")
                .build();
        String accessToken = jwtProvider.createAccessToken(member.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail());
        //when
        when(memberService.findByEmail(any())).thenReturn(member);
        when(tokenRepository.save(any())).thenReturn(Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .email("tom@gmail.com")
                .build());

        AuthenticationTokens tokens = AuthenticationTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        Token registeredRefreshToken = tokenService.registerRefreshToken(tokens);
        //then
        assertThat(registeredRefreshToken.getEmail()).isEqualTo("tom@gmail.com");
    }
}
