package com.ypdchurch.roundleafcafe.member.service;

import com.ypdchurch.roundleafcafe.common.exception.CustomApiException;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public Member registerMember(Member requestMember) throws CustomApiException {

        // 1. 동일 유저가 있는지 검사
        Optional<Member> optionalMember = memberRepository.findByEmail(requestMember.getEmail());
        if (optionalMember.isPresent()) {
            throw new CustomApiException("등록된 이메일이 존재합니다.");
        }
        // 2. password encoding
        return memberRepository.save(requestMember);
    }
}
