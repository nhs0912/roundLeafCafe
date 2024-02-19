package com.ypdchurch.roundleafcafe.common.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class JwtConfigTest {

    @Autowired
    private JwtConfig jwtConfig;
    @Test
    @DisplayName("secretKey가 존재하는지 테스트")
    void isExistSecretKey() {
        String secretKey = jwtConfig.getSecretKey();
        assertThat(secretKey).isNotNull();
    }
}