package com.ypdchurch.roundleafcafe.token.service;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @Mock
    private TokenService tokenService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private Member member;

    @BeforeEach
    public void setUp() {
        tokenService = new TokenService(tokenRepository, memberService, jwtProvider);
    }

    @Test
    @DisplayName("Token 발급 서비스 저장 성공")
    public void registerSuccessTest() {
        //given
        String refreshToken = "refreshTokenText";

//        Member member = Member.builder()
//                .id(1L)
//                .email("tom@gmail.com")
//                .build();

//        when(jwtProvider.isValidToken(any())).thenReturn(true);
//        when(jwtProvider.findMemberId(any())).thenReturn("1");
//        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
//        when(tokenRepository.save(any())).thenReturn(refreshToken);

        Token token = Token.builder()
                .refreshToken(refreshToken)
                .memberId(1L)
                .email(member.getEmail())
                .build();
        when(tokenRepository.save(any())).thenReturn(token);
        Token registeredRefreshToken = tokenService.registerRefreshToken(refreshToken);
        System.out.println(registeredRefreshToken);

        //then
//        assertThat(token.getRefreshToken()).isEqualTo(refreshToken);
    }
}
