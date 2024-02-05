//package com.ypdchurch.roundleafcafe.order.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ypdchurch.roundleafcafe.member.controller.dto.JoinRequestDto;
//import com.ypdchurch.roundleafcafe.order.service.OrderService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//
//import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith({RestDocumentationExtension.class})
//@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.roundLeafCafe.com", uriPort = 443)
//@AutoConfigureMockMvc
//@SpringBootTest
//class OrderControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private OrderService orderService;
//
//    @Test
//    @DisplayName("주문 성공 테스트")
//    void orderSuccessTest() throws Exception {
//        //give
//        ObjectMapper objectMapper = new ObjectMapper();
//        String joinRequestJson = objectMapper.writeValueAsString(joinRequestDto);
//
//        ResultActions result = mockMvc.perform(post("/api/member/join")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(joinRequestJson))
//                .andDo(MockMvcResultHandlers.print());
//
//        result.andExpect(status().isCreated())
//                .andDo(document("{class-name}/{method-name}",
//                        preprocessRequest(prettyPrint())
//                        , preprocessResponse(prettyPrint());
//}
