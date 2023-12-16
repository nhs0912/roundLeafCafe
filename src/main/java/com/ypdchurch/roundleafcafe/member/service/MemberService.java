package com.ypdchurch.roundleafcafe.member.service;

import com.ypdchurch.roundleafcafe.common.dto.ResponseDTO;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.enums.MemberGrade;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.enums.MemberStatus;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseDTO<Member> registerMember(JoinRequestDto joinRequestDto) throws IllegalAccessException {

        // 1. 동일 유저가 있는지 검사
        Optional<Member> optionalMember = memberRepository.findByEmail(joinRequestDto.email);
        if(optionalMember.isPresent()){
            throw new IllegalAccessException("중복된 이메일 입니다.");
        }
        // 2. password encoding
        String encodedPassword = bCryptPasswordEncoder.encode(joinRequestDto.getPassword());
        JoinRequestDto saveEncodePasswordJoinRequestDto = joinRequestDto.saveEncodePassword(encodedPassword);
        Member member = memberRepository.save(saveEncodePasswordJoinRequestDto.toEntity());

        // 3. dto response
        ResponseDTO<Member> responseDTO = new ResponseDTO<>();
        return responseDTO;
    }


    @Getter
    @Setter
    @Builder
    public static class JoinRequestDto {
        private String name;
        private String password;
        private String email;
        private String phoneNumber;
        private MemberGrade grade;
        private MemberRole role;
        private MemberStatus status;

        public JoinRequestDto saveEncodePassword(String encodedPassword){
            return JoinRequestDto.builder()
                    .email(this.email)
                    .password(encodedPassword)
                    .name(this.name)
                    .phoneNumber(this.phoneNumber)
                    .grade(this.grade)
                    .role(this.role)
                    .status(this.status)
                    .build();
        }

        public Member toEntity() {
            return Member.builder()
                    .name(this.name)
                    .email(this.email)
                    .password(this.password)
                    .grade(this.grade)
                    .role(this.role)
                    .build();
        }
    }


}
