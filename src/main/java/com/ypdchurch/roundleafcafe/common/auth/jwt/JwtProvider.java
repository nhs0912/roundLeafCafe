package com.ypdchurch.roundleafcafe.common.auth.jwt;


import com.ypdchurch.roundleafcafe.common.config.JwtConfig;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtProvider {
    private final JwtConfig jwtConfig;
    private static final int ONE_DAY = 1000 * 60 * 60 * 24; // 24시간
    private static final int TWO_DAY = 1000 * 60 * 60 * 48; // 48시간
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함
    public static final String HEADER = "Authorization";

    private final SecretKey secretKey;

    public JwtProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        secretKey = makeEncryptedSecretKey(jwtConfig.getSecretKey());
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
                .expiration(new Date(System.currentTimeMillis() + ONE_DAY))//a java.util.Date
                .issuedAt(new Date(System.currentTimeMillis())) // for example, now
                .claim("email", member.getEmail())
                .claim("AccessToken", accessToken)
                .signWith(secretKey)
                .compact();//just an example id
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
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            log.info("claimsJws == {}", claimsJws);
            return claimsJws;
        } catch (JwtException e) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            log.info("claimsJws isValid == {}", claimsJws);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private SecretKey makeEncryptedSecretKey(String secretKey) {
        log.info("secretKey = {}", secretKey);
        String base64SecretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
        log.info("base64SecretKey = {}", base64SecretKey);
        return Keys.hmacShaKeyFor(base64SecretKey.getBytes());
    }
}
