package com.ypdchurch.roundleafcafe.order.controller.dto;

import com.ypdchurch.roundleafcafe.order.domain.Orders;
import com.ypdchurch.roundleafcafe.order.enums.OrdersStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class OrderMenuRequest {
    private Long memberId;
    private Long basketId;
    private BigDecimal totalPrice;
    private String requests;
    private OrdersStatus ordersStatus;

    public Orders toEntity(){
        return Orders.builder()
                .memberId(this.memberId)
                .totalPrice(this.totalPrice)
                .requests(this.requests)
                .ordersStatus(OrdersStatus.ORDER_ACCEPTED)
                .build();
    }
}
