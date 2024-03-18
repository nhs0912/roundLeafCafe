package com.ypdchurch.roundleafcafe.order.service;

import com.ypdchurch.roundleafcafe.order.domain.Order;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import com.ypdchurch.roundleafcafe.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Order changeOrderStatus(Order order, OrderStatus orderStatus) {
        order.changeOrderStatus(orderStatus);
        return orderRepository.save(order);
    }
}
