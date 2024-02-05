package com.ypdchurch.roundleafcafe.member.controller.dto;

import com.ypdchurch.roundleafcafe.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinResponseDto {
    private Long id;
    private String name;
    private String email;

    public JoinResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
