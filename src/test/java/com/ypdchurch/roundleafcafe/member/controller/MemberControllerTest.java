package com.ypdchurch.roundleafcafe.member.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
//@AutoConfigureRestDocs
//@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {
//    @RegisterExtension
//    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension ("custom");
//    @Autowired
//    protected RestDocumentationResultHandler restDocs;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

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
        JSONObject json = new JSONObject();
        json.put("name", "tom");
        json.put("password", "1234");
        json.put("email", "tom@gmail.com");
        json.put("phoneNumber", "01012345678");

        mockMvc.perform(post("/api/join")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andDo(document(
                        "{class-name}/{method-name}",
                        preprocessRequest(prettyPrint())
                        , preprocessResponse(prettyPrint())

//                , restDocs.document(
//                                RequestDocumentation.queryParameters(
//                                        RequestDocumentation.parameterWithName("name").description("이름"),
//                                        RequestDocumentation.parameterWithName("password").description("password"),
//                                        RequestDocumentation.parameterWithName("email").description("email"),
//                                        RequestDocumentation.parameterWithName("phoneNumber").description("phoneNumber")
//
//                                )
//                        ))
                ));
    }
}