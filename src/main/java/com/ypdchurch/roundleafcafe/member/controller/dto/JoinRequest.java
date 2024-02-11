package com.ypdchurch.roundleafcafe.member.controller.dto;

import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.enums.MemberGrade;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.enums.MemberStatus;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class JoinRequest {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private MemberGrade grade;
    private MemberRole role;
    private MemberStatus status;

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
