package com.ypdchurch.roundleafcafe.common.auth.jwt;

import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.enums.MemberGrade;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.enums.MemberStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class JwtProviderTest {
    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("token 생성 테스트")
    void createTokenSuccessTest() {
        Member tom = Member.builder()
                .id(1L)
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .grade(MemberGrade.NORMAL)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.ACTIVE)
                .build();
        String accessToken = jwtProvider.createAccessToken(tom);
        assertThat(accessToken).isNotNull();
    }

    @Test
    @DisplayName("refresh token 생성 테스트")
    void createRefreshTokenSuccessTest() {
        Member tom = Member.builder()
                .id(1L)
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .grade(MemberGrade.NORMAL)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.ACTIVE)
                .build();
        String accessToken = jwtProvider.createAccessToken(tom);
        String refreshToken = jwtProvider.createRefreshToken(tom, accessToken);
        assertThat(refreshToken).isNotNull();
    }

    @Test
    @DisplayName("token 확인 테스트")
    void verifyTokenSuccessTest() {
        Member tom = Member.builder()
                .id(1L)
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .grade(MemberGrade.NORMAL)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.ACTIVE)
                .build();
        String accessToken = jwtProvider.createAccessToken(tom);
        Jws<Claims> claims = jwtProvider.verify(accessToken);
        Claims payload = claims.getPayload();
        assertAll(
                () -> assertThat(payload.getSubject()).isEqualTo("1"),
                () -> assertThat(payload.get("email")).isEqualTo("tom@gmail.com"),
                () -> assertThat(payload.get("grade")).isEqualTo("NORMAL"),
                () -> assertThat(payload.get("role")).isEqualTo("CUSTOMER"),
                () -> assertThat(payload.get("status")).isEqualTo("ACTIVE")
        );
    }
}