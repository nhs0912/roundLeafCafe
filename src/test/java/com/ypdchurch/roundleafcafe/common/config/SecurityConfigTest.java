package com.ypdchurch.roundleafcafe.common.config;

import com.ypdchurch.roundleafcafe.common.annotation.WithMockJwtUser;
import com.ypdchurch.roundleafcafe.token.domain.Token;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.thymeleaf.util.StringUtils;

import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

//가짜 환경에서 테스트
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SecurityConfigTest {
    //가짜 경에 등록된 MockMVC를 DI함
    @Autowired
    private MockMvc mvc;

//    @Test
    @WithMockJwtUser
    public void authentication_test() throws Exception {
        //given
        Token token = Token.builder()
                .accessToken("1234")
                .refreshToken("1234")
                .build();

        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/admin"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();
        assertThat(httpStatusCode).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        //then
    }

}