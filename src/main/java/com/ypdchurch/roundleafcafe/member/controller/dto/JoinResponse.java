package com.ypdchurch.roundleafcafe.member.controller.dto;

import com.ypdchurch.roundleafcafe.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinResponse {
    private Long id;
    private String name;
    private String email;
    public JoinResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
