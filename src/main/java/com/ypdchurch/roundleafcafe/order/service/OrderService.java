package com.ypdchurch.roundleafcafe.order.service;

import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order orderMenu(Order order) {
        log.info("orders.orderMenu = {}", order);
        return orderRepository.save(order);
    }
}
