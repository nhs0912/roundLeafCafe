package com.ypdchurch.roundleafcafe.common.config;

import com.ypdchurch.roundleafcafe.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class MemberPrincipal extends User {
    private final Member member;

    public MemberPrincipal(Member member) {
        super(member.getEmail(), member.getPassword(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_" + member.getRole().name())
                ));
        this.member = member;
    }


}
