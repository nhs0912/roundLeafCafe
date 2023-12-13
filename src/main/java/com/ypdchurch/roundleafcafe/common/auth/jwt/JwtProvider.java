package com.ypdchurch.roundleafcafe.common.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.JWT.require;


@Component
public class JwtProvider {
    private static final String SUBJECT = "roundleafcafe";
    private static final int ONE_DAY = 1000 * 60 * 60 * 24; // 24시간
    private static final int TWO_DAY = 1000 * 60 * 60 * 48; // 48시간
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함
    public static final String HEADER = "Authorization";

    public static String createAccessToken(Member member) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + ONE_DAY))
                .withClaim("id", member.getId())
                .withClaim("role", member.getRole().name())
                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static String createRefreshToken(Member member, String accessToken) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + TWO_DAY))
                .withClaim("id", member.getId())
                .withClaim("AccessToken", accessToken)
                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = require(Algorithm.HMAC512(JwtVO.SECRET))
                .build().verify(jwt);
        return decodedJWT;
    }
}
