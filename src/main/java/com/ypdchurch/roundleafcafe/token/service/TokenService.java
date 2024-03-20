package com.ypdchurch.roundleafcafe.token.service;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
import com.ypdchurch.roundleafcafe.common.config.MemberPrincipal;
import com.ypdchurch.roundleafcafe.common.exception.TokenCustomException;
import com.ypdchurch.roundleafcafe.common.exception.code.TokenErrorCode;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
import com.ypdchurch.roundleafcafe.token.domain.AuthenticationTokens;
import com.ypdchurch.roundleafcafe.token.domain.Token;
import com.ypdchurch.roundleafcafe.token.enums.TokenStatus;
import com.ypdchurch.roundleafcafe.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    public Optional<Token> findByRefreshToken(String token) {
        return tokenRepository.findByRefreshToken(token);
    }

    public Optional<Token> findByAccessToken(String token) {
        return tokenRepository.findByAccessToken(token);
    }

    public Optional<Token> findByEmail(String email) {
        return tokenRepository.findByEmail(email);
    }

    public Optional<Token> findByMemberId(Long memberId) {
        return tokenRepository.findByMemberId(memberId);
    }

    public Token registerRefreshToken(AuthenticationTokens token, Member member) {
        Token refreshToken = Token.builder()
                .refreshToken(token.getRefreshToken())
                .accessToken(token.getAccessToken())
                .memberId(member.getId())
                .email(member.getEmail())
                .status(TokenStatus.ACTIVE)
                .build();
        return tokenRepository.save(refreshToken);
    }

//    public Token registerRefreshToken(String token) {
//        return processToken(token, block -> {
//            Optional<String> memberIdText = jwtProvider.findMemberId(token);
//            if (memberIdText.isPresent()) {
//                Long memberId = Long.valueOf(memberIdText.get());
//                Member foundMember = memberService.findById(memberId);
//                Token refreshToken = Token.builder()
//                        .refreshToken(token)
//                        .memberId(memberId)
//                        .email(foundMember.getEmail())
//                        .status(TokenStatus.ACTIVE)
//                        .build();
//                return tokenRepository.save(refreshToken);
//            }
//            return null;
//        });
//    }

    public Token updateRefreshToken(Token token) {
        if (!token.isValidRefreshToken()) {
            throw new TokenCustomException(TokenErrorCode.INVALID_TOKEN);
        }
        Optional<Token> refreshTokenOptional = findByRefreshToken(token.getRefreshToken());
        String newRefreshToken = jwtProvider.createRefreshToken(token.getEmail());
        return refreshTokenOptional.get().updateRefreshToken(newRefreshToken);
    }

//    public Token processToken(String token, ProcessTokenProcess block) {
//        if (!jwtProvider.isValidToken(token)) {
//            throw new TokenCustomException(TokenErrorCode.INVALID_TOKEN);
//        }
//        return block.process(token);
//    }

    public AuthenticationTokens getAuthenticationTokens(String email) {
        String refreshToken = jwtProvider.createRefreshToken(email);
        String accessToken = jwtProvider.createAccessToken(email);
        return AuthenticationTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void deleteByRefreshToken(String token) {
        Token refreshToken = findByRefreshToken(token)
                .get();
        tokenRepository.delete(refreshToken);
    }

    public void deleteByEmail(String email) {
        Token refreshToken = findByEmail(email).get();
        this.deleteByRefreshToken(refreshToken.getRefreshToken());
    }

    public void deleteByMemberId(Long memberId) {
        Token refreshToken = findByMemberId(memberId).get();
        this.deleteByRefreshToken(refreshToken.getRefreshToken());
    }

    private interface ProcessTokenProcess {
        abstract Token process(String token);
    }
}
