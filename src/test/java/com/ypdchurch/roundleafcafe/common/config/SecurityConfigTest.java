package com.ypdchurch.roundleafcafe.common.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//가짜 환경에서 테스트
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SecurityConfigTest {
    //가짜 경에 등록된 MockMVC를 DI함
    @Autowired
    private MockMvc mvc;

    @Test
    public void authentication_test() throws Exception {
        //given
        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/admin"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();
        System.out.println("test heeseok: " + responseBody);
        System.out.println("test heeseok: " + httpStatusCode);
        Assertions.assertThat(httpStatusCode).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        //then
    }

}