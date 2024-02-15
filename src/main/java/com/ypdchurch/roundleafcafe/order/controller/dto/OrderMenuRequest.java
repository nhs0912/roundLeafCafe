package com.ypdchurch.roundleafcafe.order.controller.dto;

import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderMenuRequest {
    @NotNull
    private Long memberId;
    @NotNull
    private Long basketId;
    @NotNull
    private BigDecimal totalPrice;
    @NotEmpty
    private String requests;
    private OrderStatus orderStatus;

    public Order toEntity() {
        return Order.builder()
                .memberId(this.getMemberId())
                .totalPrice(this.getTotalPrice())
                .requests(this.getRequests())
                .orderStatus(this.getOrderStatus())
                .basketId(this.getBasketId())
                .build();
    }
}
