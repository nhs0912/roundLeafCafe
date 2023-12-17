package com.ypdchurch.roundleafcafe.member.service;

import com.ypdchurch.roundleafcafe.common.dto.ResponseDTO;
import com.ypdchurch.roundleafcafe.common.exception.CustomApiException;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.enums.MemberGrade;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.enums.MemberStatus;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import lombok.*;
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


    @Getter
    @Setter
    public static class JoinResponseDto {
        private Long id;
        private String name;
        private String email;

        public JoinResponseDto(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.email = member.getEmail();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class JoinRequestDto {
        private String name;
        private String password;
        private String email;
        private String phoneNumber;
        private MemberGrade grade;
        private MemberRole role;
        private MemberStatus status;

        @Builder
        public JoinRequestDto(String name, String password, String email, String phoneNumber) {
            this.name = name;
            this.password = password;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public Member toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
            return Member.builder()
                    .name(this.name)
                    .email(this.email)
                    .password(bCryptPasswordEncoder.encode(this.password))
                    .phoneNumber(this.phoneNumber)
                    .grade(this.grade)
                    .role(this.role)
                    .status(MemberStatus.ACTIVE)
                    .build();
        }
    }


}
