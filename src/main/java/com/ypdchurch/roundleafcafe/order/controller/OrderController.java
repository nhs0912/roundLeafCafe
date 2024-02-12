package com.ypdchurch.roundleafcafe.order.controller;

import com.ypdchurch.roundleafcafe.order.controller.dto.OrderMenuRequest;
import com.ypdchurch.roundleafcafe.order.domain.Orders;
import com.ypdchurch.roundleafcafe.order.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrdersService ordersService;

    @GetMapping("orderMenu")
    public String moveOrderPage() {
        return "order";
    }

    @PostMapping("/orderMenu")
    public Orders orderMenu(@RequestBody OrderMenuRequest orderMenuRequest) {
        log.info("orderMenu 시작 = {} ", orderMenuRequest);
        validateInputOrderMenu(orderMenuRequest);
        Orders orders = orderMenuRequest.toEntity();
        return ordersService.orderMenu(orders);
    }

    private void validateInputOrderMenu(OrderMenuRequest orderMenuRequest) {
        if (orderMenuRequest.getMemberId() == null) {
            throw new IllegalArgumentException("멤버 ID가 없습니다.");
        }

        if (orderMenuRequest.getBasketId() == null) {
            throw new IllegalArgumentException("장바구니 ID가 없습니다.");
        }

        if (orderMenuRequest.getTotalPrice() == null || orderMenuRequest.getTotalPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("가격은 마이너스이거나 0원 일 수 없습니다.");
        }
    }
}
