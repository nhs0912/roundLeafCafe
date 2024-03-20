package com.ypdchurch.roundleafcafe.token.repository;

import com.ypdchurch.roundleafcafe.token.domain.Token;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByMemberId(@NotEmpty Long memberId);

    Optional<Token> findByEmail(@NotEmpty String email);

    Optional<Token> findByRefreshToken(@NotEmpty String refreshToken);
    Optional<Token> findByAccessToken(@NotEmpty String accessToken);

}
