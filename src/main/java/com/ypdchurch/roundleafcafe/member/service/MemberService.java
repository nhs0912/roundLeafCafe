package com.ypdchurch.roundleafcafe.member.service;

import com.ypdchurch.roundleafcafe.common.exception.CustomApiException;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import com.ypdchurch.roundleafcafe.member.service.dto.JoinRequestDto;
import com.ypdchurch.roundleafcafe.member.service.dto.JoinResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public JoinResponseDto registerMember(JoinRequestDto joinRequestDto) throws IllegalAccessException {

        // 1. 동일 유저가 있는지 검사
        Optional<Member> optionalMember = memberRepository.findByEmail(joinRequestDto.getEmail());
        if (optionalMember.isPresent()) {
            throw new CustomApiException("등록된 이메일이 존재합니다.");
        }
        // 2. password encoding
        Member member = memberRepository.save(joinRequestDto.toEntity(passwordEncoder));

        // 3. dto response
        return new JoinResponseDto(member);
    }
}
