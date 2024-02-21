package com.ypdchurch.roundleafcafe.member.service;

import com.ypdchurch.roundleafcafe.common.exception.MemberCustomException;
import com.ypdchurch.roundleafcafe.common.exception.MemberErrorCode;
import com.ypdchurch.roundleafcafe.member.controller.dto.JoinRequest;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 테스트")
    public void registerMemberTest() throws IllegalAccessException {
        //given
        JoinRequest joinRequest = JoinRequest.builder()
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .build();

        //stub1
        when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());

        //stub2
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
    public void registerMemberDuplicationCheckTest() throws IllegalAccessException {
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
            if (requestMember != null) {
                memberService.registerMember(requestMember);
            }
        })
                .isInstanceOf(MemberCustomException.class)
                .hasMessage(MemberErrorCode.ALREADY_EXIST_EMAIL.getMessage());
    }




}
