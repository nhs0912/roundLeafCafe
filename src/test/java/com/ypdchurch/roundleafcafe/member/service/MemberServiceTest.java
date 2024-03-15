package com.ypdchurch.roundleafcafe.member.service;

import com.ypdchurch.roundleafcafe.common.exception.MemberCustomException;
import com.ypdchurch.roundleafcafe.common.exception.code.MemberErrorCode;
import com.ypdchurch.roundleafcafe.member.controller.dto.JoinRequest;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class MemberServiceTest {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;


    @Test
    @DisplayName("회원 가입 테스트")
    public void registerMemberTest() {
        //given
        Member member = Member.builder()
                .id(1L)
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .role(MemberRole.CUSTOMER)
                .build();

        when(memberRepository.save(any())).thenReturn(member);

        //when
        Member registeredMember = memberService.registerMember(member);
        //then
        assertSoftly(softly -> {
                    softly.assertThat(registeredMember.getId()).isEqualTo(1L);
                    softly.assertThat(registeredMember.getName()).isEqualTo("tom");
                    softly.assertThat(registeredMember.getEmail()).isEqualTo("tom@gmail.com");
                }
        );
    }

    @Test
    @DisplayName("회원 가입 중복체크 테스트 ")
    public void registerMemberDuplicationCheckTest() {
        //given
        JoinRequest joinRequest = JoinRequest.builder()
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .build();

        Member requestMember = joinRequest.toEntity(passwordEncoder);

        //stub1
        when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(requestMember));
        //when
        //then
        assertThatThrownBy(() ->
        {
            assert requestMember != null : "requestMember가 null입니다.";
            memberService.registerMember(requestMember);

        })
                .isInstanceOf(MemberCustomException.class)
                .hasMessage(MemberErrorCode.ALREADY_EXIST_EMAIL.getMessage());
    }

    @Test
    @DisplayName("멤버 이메일로 찾기 성공 테스트")
    void findByEmailSuccessTest() {
        //given
        JoinRequest joinRequest = JoinRequest.builder()
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .build();

        Member requestMember = joinRequest.toEntity(passwordEncoder);
        //stub1
        when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(requestMember));

        Member foundMember = memberService.findByEmail("tom@gmail.com");
        assertThat(foundMember.getEmail()).isEqualTo(requestMember.getEmail());
    }

    @Test
    @DisplayName("멤버 이메일로 찾기 실패 테스트")
    void findByEmailFailTest() {
        //given
        //stub1
        assertThatThrownBy(() -> {
            memberService.findByEmail("tom@gmail.com");
        }).isInstanceOf(MemberCustomException.class)
                .hasMessage(MemberErrorCode.MEMBER_NOT_FOUND.getMessage());

    }

}
