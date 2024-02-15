package com.ypdchurch.roundleafcafe.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.enums.MemberGrade;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.enums.MemberStatus;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
import com.ypdchurch.roundleafcafe.order.controller.dto.OrderMenuRequest;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import com.ypdchurch.roundleafcafe.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.roundLeafCafe.com", uriPort = 443)
@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private MemberService memberService;


    @Test
    @DisplayName("주문 성공 테스트")
    void orderSuccessTest() throws Exception {
        //give

        Member tom = Member.builder()
                .name("tom")
                .password("1234")
                .email("tom@gmail.com")
                .phoneNumber("01012345678")
                .grade(MemberGrade.NORMAL)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.ACTIVE)
                .id(1L)
                .build();

        when(memberService.registerMember(any()))
                .thenReturn(tom);

        OrderMenuRequest orderMenuRequest = OrderMenuRequest.builder()
                .memberId(tom.getId())
                .basketId(1L)
                .totalPrice(new BigDecimal(2000))
                .orderStatus(OrderStatus.ORDER_ACCEPTED)
                .requests("요청사항이 많습니다!")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String orderMenuRequestJson = objectMapper.writeValueAsString(orderMenuRequest);

        ResultActions result = mockMvc.perform(post("/api/order/menu")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderMenuRequestJson))
                .andDo(MockMvcResultHandlers.print());

        result.andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint())
                        , preprocessResponse(prettyPrint())
                        , requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 등록 아이디"),
                                fieldWithPath("basketId").type(JsonFieldType.NUMBER).description("장바구니 아이디"),
                                fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("총가격"),
                                fieldWithPath("requests").type(JsonFieldType.STRING).description("요청사항").optional(),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문상태")

                        ),responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("주문 아이디"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 등록 아이디"),
                                fieldWithPath("basketId").type(JsonFieldType.NUMBER).description("장바구니 아이디"),
                                fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("총가격"),
                                fieldWithPath("requests").type(JsonFieldType.STRING).description("요청사항"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문상태")
                        )
                ));
    }
}
