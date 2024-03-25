package com.ypdchurch.roundleafcafe.order.controller.dto;

import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@ToString
public class OrderMenuRequest {
    @NotNull

    private Long memberId;
    @NotNull
    private Long basketId;
    @NotNull
    private BigDecimal totalPrice;
    @NotBlank
    private String requests;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderMenuRequest(Long memberId, Long basketId, BigDecimal totalPrice, String requests, OrderStatus orderStatus) {
        this.memberId = memberId;
        this.basketId = basketId;
        this.totalPrice = totalPrice;
        this.requests = requests;
        this.orderStatus = orderStatus;
    }

    private void checkTotalPrice(BigDecimal totalPrice) {

    }

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
