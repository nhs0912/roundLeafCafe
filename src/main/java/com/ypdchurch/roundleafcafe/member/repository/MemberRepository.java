package com.ypdchurch.roundleafcafe.member.repository;

import com.ypdchurch.roundleafcafe.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(@Param("email")String email);
}
