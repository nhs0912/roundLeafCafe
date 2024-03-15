package com.ypdchurch.roundleafcafe.token.service;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
import com.ypdchurch.roundleafcafe.token.domain.Token;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class TokenServiceTest {
    @Autowired
    private TokenService tokenService;

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("Token 발급 서비스 저장 성공")
    public void registerSuccessTest() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("tom@gmail.com")
                .build();
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail());


        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
//doReturn(model).when(this.modelRepository).save(model);
        doReturn(member.getId()).when(memberService.findByEmail(any()));
        when(memberService.findById(any())).thenReturn(member);
        Token token = tokenService.registerRefreshToken(refreshToken);

        assertThat(token.getEmail()).isEqualTo("tom@gmail.com");
    }
}
