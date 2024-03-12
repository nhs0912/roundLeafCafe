package com.ypdchurch.roundleafcafe.member.service;

import com.ypdchurch.roundleafcafe.common.exception.MemberCustomException;
import com.ypdchurch.roundleafcafe.common.exception.code.MemberErrorCode;
import com.ypdchurch.roundleafcafe.member.controller.dto.SigninRequest;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private static MemberCustomException get() {
        return new MemberCustomException(MemberErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Transactional
    public Member registerMember(Member requestMember) {

        // 1. 동일 유저가 있는지 검사
        if (isExistMember(requestMember)) {
            throw new MemberCustomException(MemberErrorCode.ALREADY_EXIST_EMAIL.getMessage());
        }
        // 2. password encoding
        return memberRepository.save(requestMember);
    }

    public Long findEmailAndPassword(SigninRequest signinRequest) {
        Member member = memberRepository.findByEmailAndPassword(signinRequest.getEmail(), signinRequest.getPassword())
                .orElseThrow(() -> new MemberCustomException(MemberErrorCode.ID_AND_PASSWORD_IS_WRONG.getMessage()));
        return member.getId();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberService::get);
    }

    private boolean isExistMember(Member member) {
        return memberRepository.findByEmail(member.getEmail())
                .isPresent();
    }
}
