package com.ypdchurch.roundleafcafe.order.service;

import com.ypdchurch.roundleafcafe.member.repository.MemberRepository;
import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import com.ypdchurch.roundleafcafe.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문 상태 접수 성공 테스트")
    void changeOrderStatusTest() {
        //given
        Order order = Order.builder()
                .id(1L)
                .basketId(1L)
                .orderStatus(OrderStatus.ORDER_START)
                .totalPrice(new BigDecimal("245000"))
                .memberId(1L)
                .build();
        //when
        Order acceptedOrder = order.accepted();
        when(orderRepository.save(any())).thenReturn(acceptedOrder);

        Order savedOrder = orderService.save(acceptedOrder);

        //then
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER_ACCEPTED);

    }
}