package com.ypdchurch.roundleafcafe.order.service;

import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import com.ypdchurch.roundleafcafe.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
//    @ParameterizedTest
    @DisplayName("주문 상태 변경 성공 테스트")
//    @EnumSource(value = OrderStatus.class, names = {"ORDER_ACCEPTED", "ORDER_CONFIRMED", "COOKING", "MENU_ALREADY", "PICK_UP_COMPLETE", "WHOLE_COMPLETE"})
//    void changeOrderStatusTest(OrderStatus orderStatus) {
    void changeOrderStatusTest() {
        //given
        Order order = Order.builder()
                .id(1L)
                .basketId(1L)
                .orderStatus(OrderStatus.ORDER_ACCEPTED)
                .totalPrice(new BigDecimal("245000"))
                .memberId(1L)
                .build();
        //when
        orderService.changeOrderStatus(order, OrderStatus.ORDER_CONFIRMED);

        //then
        assertThat(order.getOrderStatus().getCode()).isEqualTo(OrderStatus.ORDER_CONFIRMED.getCode());
    }
}