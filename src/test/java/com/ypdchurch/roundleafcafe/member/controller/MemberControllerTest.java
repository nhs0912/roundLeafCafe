package com.ypdchurch.roundleafcafe.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypdchurch.roundleafcafe.member.service.dto.JoinRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.roundLeafCafe.com", uriPort = 443)
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("api doc test sample")
    void sampleTest() throws Exception {
        mockMvc.perform(get("/api/join")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버 가입 성공 테스트")
    void joinSuccessTest() throws Exception {

        //give
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String joinRequestJson = objectMapper.writeValueAsString(joinRequestDto);

        ResultActions result = mockMvc.perform(post("/api/join")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joinRequestJson))
                .andDo(MockMvcResultHandlers.print());

        result.andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint())
                        , preprocessResponse(prettyPrint())
//                        , requestFields(
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
//                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("phoneNumber")
//                        ), responseFields(
//                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("response status"),
//                                fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
//                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id number"),
//                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
//                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email")
//                        )
                ));
    }
}
