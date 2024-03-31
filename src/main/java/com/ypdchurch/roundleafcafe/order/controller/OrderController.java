package com.ypdchurch.roundleafcafe.order.controller;

import com.ypdchurch.roundleafcafe.order.controller.dto.OrderMenuRequest;
import com.ypdchurch.roundleafcafe.order.controller.dto.OrderMenuResponse;
import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/order")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @GetMapping("menu/{menu}")
    public String moveOrderPage(@RequestParam String menuKindCode) {
        log.info("menuKindCode = {}", menuKindCode);

        return "order";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("menu")
    public OrderMenuResponse orderMenu(@RequestBody OrderMenuRequest orderMenuRequest) {
        log.info("orderMenu 시작 = {} ", orderMenuRequest);
        Order order = orderMenuRequest.toEntity();
        Order orderedMenu = orderService.orderMenu(order);
        return OrderMenuResponse.of(orderedMenu);
    }



}
