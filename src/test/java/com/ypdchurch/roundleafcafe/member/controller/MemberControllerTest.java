package com.ypdchurch.roundleafcafe.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypdchurch.roundleafcafe.member.controller.dto.JoinRequest;
import com.ypdchurch.roundleafcafe.member.enums.MemberGrade;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.enums.MemberStatus;
import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.roundLeafCafe.com", uriPort = 443)
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("api doc test sample")
    void sampleTest() throws Exception {
        mockMvc.perform(get("/api/member/join")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버 가입 성공 테스트")
    void joinSuccessTest() throws Exception {

        //give
        JoinRequest joinRequest = JoinRequest.builder()
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .grade(MemberGrade.NORMAL)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.ACTIVE)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String joinRequestJson = objectMapper.writeValueAsString(joinRequest);

        ResultActions result = mockMvc.perform(post("/api/member/join")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joinRequestJson))
                .andDo(MockMvcResultHandlers.print());

        result.andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint())
                        , preprocessResponse(prettyPrint())
                        , requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("핸드폰번호"),
                                fieldWithPath("grade").type(JsonFieldType.STRING).description("등급"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("역할"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태")
                        ), responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email")
                        )
                ));
    }

    @Test
    @DisplayName("멤버 가입 실패 테스트")
    void joinFailTest() throws Exception {

        //give
        JoinRequest joinRequest = JoinRequest.builder()
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .grade(MemberGrade.NORMAL)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.ACTIVE)
                .build();

        memberRepository.save(joinRequest.toEntity(passwordEncoder));
        ObjectMapper objectMapper = new ObjectMapper();

        JoinRequest joinAnotherRequest = JoinRequest.builder()
                .name("Jane")
                .password("12345")
                .email("tom@gmail.com")
                .phoneNumber("01043218765")
                .grade(MemberGrade.NORMAL)
                .role(MemberRole.ADMIN)
                .status(MemberStatus.ACTIVE)
                .build();
        String joinAnotherRequestJson = objectMapper.writeValueAsString(joinAnotherRequest);


        ResultActions anotherResult = mockMvc.perform(post("/api/member/join")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joinAnotherRequestJson))
                .andExpect(status().is4xxClientError());

//        anotherResult.andExpect(status().is4xxClientError())
//                .andDo(document("{class-name}/{method-name}",
//                        preprocessRequest(prettyPrint())
//                        , preprocessResponse(prettyPrint())
//                        , requestFields(
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
//                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("핸드폰번호"),
//                                fieldWithPath("grade").type(JsonFieldType.STRING).description("등급"),
//                                fieldWithPath("role").type(JsonFieldType.STRING).description("역할"),
//                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태")
//                        ), responseFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("email")
//                        )
//                ));
    }
}
