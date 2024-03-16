package com.ypdchurch.roundleafcafe.token.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.token.enums.TokenStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@Entity(name = "token")
public class Token extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", updatable = false)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @NotEmpty
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "access_token", nullable = false, length = 512)
    private String accessToken;

    @NotEmpty
    @Column(name = "refresh_token", nullable = false, length = 512)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @Column(updatable = false)
    private LocalDateTime endAt;

    public Token updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}
