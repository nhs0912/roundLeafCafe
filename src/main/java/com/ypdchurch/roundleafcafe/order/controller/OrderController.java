package com.ypdchurch.roundleafcafe.order.controller;

import com.ypdchurch.roundleafcafe.order.controller.dto.OrderMenuRequest;
import com.ypdchurch.roundleafcafe.order.controller.dto.OrderMenuResponse;
import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @GetMapping("menu")
    public String moveOrderPage() {
        return "order";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("menu")
    public OrderMenuResponse orderMenu(@RequestBody OrderMenuRequest orderMenuRequest) {
        log.info("orderMenu 시작 = {} ", orderMenuRequest);
        validateInputOrderMenu(orderMenuRequest);
        Order order = orderMenuRequest.toEntity();
        Order orderedMenu = orderService.orderMenu(order);
        return OrderMenuResponse.of(orderedMenu);
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
