package com.ypdchurch.roundleafcafe.common.auth.jwt;


import com.ypdchurch.roundleafcafe.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

import static io.jsonwebtoken.SignatureAlgorithm.*;

@Component
public class JwtProvider {
    private static final String SUBJECT = "roundleafcafe_2024";
    private static final int ONE_DAY = 1000 * 60 * 60 * 24; // 24시간
    private static final int TWO_DAY = 1000 * 60 * 60 * 48; // 48시간
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함
    public static final String HEADER = "Authorization";
    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    public static String createAccessToken(Member member) {

        String jwt = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(SUBJECT)
                .expiration(new Date(System.currentTimeMillis() + ONE_DAY))//a java.util.Date
                .issuedAt(new Date(System.currentTimeMillis())) // for example, now
                .claim("id", member.getId())
                .claim("role", member.getRole().name())

                .signWith(key)
                .compact();//just an example id

        /// ... etc ...

//        String jwt = JWT.create()
//                .withSubject(SUBJECT)
//                .withExpiresAt(new Date(System.currentTimeMillis() + ONE_DAY))
//                .withClaim("id", member.getId())
//                .withClaim("role", member.getRole().name())
//                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static String createRefreshToken(Member member, String accessToken) {

        String jwt = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(SUBJECT)
                .expiration(new Date(System.currentTimeMillis() + ONE_DAY))//a java.util.Date
                .issuedAt(new Date(System.currentTimeMillis())) // for example, now
                .claim("id", member.getId())
                .claim("AccessToken", accessToken)
                .signWith(key)
                .compact();//just an example id

//        String jwt = JWT.create()
//                .withSubject(SUBJECT)
//                .withExpiresAt(new Date(System.currentTimeMillis() + TWO_DAY))
//                .withClaim("id", member.getId())
//                .withClaim("AccessToken", accessToken)
//                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return TOKEN_PREFIX + jwt;
    }

//    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
//        DecodedJWT decodedJWT = require(Algorithm.HMAC512(JwtVO.SECRET))
//                .build().verify(jwt);
//        return decodedJWT;
//    }
}
