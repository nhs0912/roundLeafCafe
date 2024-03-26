package com.ypdchurch.roundleafcafe.order.controller.dto;

import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class OrderMenuResponse {
    private Long id;
    private Long memberId;
    private Long basketId;
    private BigDecimal totalPrice;
    private String requests;
    private OrderStatus orderStatus;

    public static OrderMenuResponse of(Order order) {
        return OrderMenuResponse.builder()
                .id(order.getId())
                .basketId(order.getOrderItemId())
                .memberId(order.getMemberId())
                .orderStatus(order.getStatus())
                .requests(order.getRequests())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
