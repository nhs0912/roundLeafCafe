package com.ypdchurch.roundleafcafe.token.service;

import com.ypdchurch.roundleafcafe.token.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("Token 발급 서비스 저장 성공")
    public void registerSuccessTest() {

//        String Token = "1234"
//        tokenService.registerRefreshToken()
    }

}
