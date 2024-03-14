package com.ypdchurch.roundleafcafe.common.auth.jwt;


import com.ypdchurch.roundleafcafe.common.config.JwtConfig;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.token.domain.Token;
import com.ypdchurch.roundleafcafe.token.enums.TokenStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private static final int ONE_DAY = 1000 * 60 * 60 * 24; // 24시간
    private static final int TWO_DAY = 1000 * 60 * 60 * 48; // 48시간
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.secretKey = makeEncryptedSecretKey(jwtConfig.getSecretKey());
    }

    public String createAccessToken(Member member) {
        log.info("jwtConfig createAcces sToken= {}", jwtConfig);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .header()
                .type("JWT")
                .and()
                .subject(member.getId().toString())
                .expiration(new Date(System.currentTimeMillis() + ONE_DAY))//a java.util.Date
                .issuedAt(new Date(System.currentTimeMillis())) // for example, now
                .claim("email", member.getEmail())
                .claim("grade", member.getGrade())
                .claim("role", member.getRole().name())
                .claim("status", member.getStatus())
                .signWith(secretKey)
                .compact();//just an example id
    }

    public String createAccessToken(String email) {
        log.info("jwtConfig createAcces email Token= {}", jwtConfig);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .header()
                .type("JWT")
                .and()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + ONE_DAY))//a java.util.Date
                .issuedAt(new Date(System.currentTimeMillis())) // for example, now
                .claim("email", email)
                .signWith(secretKey)
                .compact();//just an example id
    }

    public String createRefreshToken(Member member, String accessToken) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(member.getId().toString())
                .expiration(new Date(System.currentTimeMillis() + TWO_DAY))//a java.util.Date
                .issuedAt(new Date(System.currentTimeMillis())) // for example, now
                .claim("email", member.getEmail())
                .claim("AccessToken", accessToken)
                .signWith(secretKey)
                .compact();//just an example id
    }

    public Token createRefreshToken(Member member) {
        String refreshToken = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(member.getId().toString())
                .expiration(new Date(System.currentTimeMillis() + TWO_DAY))//a java.util.Date
                .issuedAt(new Date(System.currentTimeMillis())) // for example, now
                .claim("email", member.getEmail())
                .claim("memberId", member.getId())
                .signWith(secretKey)
                .compact();//just an example id

        return Token.builder()
                .memberId(member.getId())
                .refreshToken(refreshToken)
                .email(member.getEmail())
                .status(TokenStatus.ACTIVE)
                .build();
    }

    public String createRefreshToken(String email, String accessToken) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + ONE_DAY))//a java.util.Date
                .issuedAt(new Date(System.currentTimeMillis())) // for example, now
                .claim("email", email)
                .claim("AccessToken", accessToken)
                .signWith(secretKey)
                .compact();//just an example id
    }

    public Jws<Claims> verify(String token) {
        try {
            Jws<Claims> claimsJws = getClaims(token);
            log.info("claimsJws == {}", claimsJws);
            return claimsJws;
        } catch (JwtException e) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = getClaims(token);
            log.info("claimsJws isValid == {}", claimsJws);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Optional<String> findMemberId(String token) {
        Jws<Claims> claimsJws = getClaims(token);
        return Optional.ofNullable(claimsJws.getPayload().get("memberId").toString());
    }

    public String findEmail(String token) {
        Jws<Claims> claimsJws = getClaims(token);
        return claimsJws.getPayload()
                .get("email").toString();
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }

    private SecretKey makeEncryptedSecretKey(String secretKey) {
        String base64SecretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(base64SecretKey.getBytes());
    }
}
