package com.ypdchurch.roundleafcafe.token.domain;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.common.exception.TokenCustomException;
import com.ypdchurch.roundleafcafe.common.exception.code.TokenErrorCode;
import com.ypdchurch.roundleafcafe.token.enums.TokenStatus;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Base64;

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

    public Token updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Token updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public boolean isValidAccessToken(String secretKey) {
        return this.isValidToken(this.getAccessToken(), secretKey);
    }

    public boolean isValidRefreshToken(String secretKey) {
        return this.isValidToken(this.getRefreshToken(), secretKey);
    }

    private boolean isValidToken(String token, String secretKey) {
        try {
            Jws<Claims> claimsJws = getClaims(token, secretKey);
            return true;
        } catch (SecurityException | MalformedJwtException | io.jsonwebtoken.security.SignatureException e) {
            throw new TokenCustomException(TokenErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new TokenCustomException(TokenErrorCode.TOKEN_IS_EXPIRED);
        }
    }

    private Jws<Claims> getClaims(String token, String secretKey) {
        return Jwts.parser()
                .verifyWith(makeEncryptedSecretKey(secretKey))
                .build()
                .parseSignedClaims(token);
    }

    private SecretKey makeEncryptedSecretKey(String secretKey) {
        String base64SecretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(base64SecretKey.getBytes());
    }
}
