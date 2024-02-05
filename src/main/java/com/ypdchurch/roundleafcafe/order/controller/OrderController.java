package com.ypdchurch.roundleafcafe.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/order")
@RestController
public class OrderController {

    @GetMapping("order")
    public String moveOrderPage() {
        return "order";
    }

    @PostMapping("order")
    public String orderMenu(){
        return "order";
    }
}
