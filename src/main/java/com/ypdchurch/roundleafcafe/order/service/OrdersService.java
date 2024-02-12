package com.ypdchurch.roundleafcafe.order.service;

import com.ypdchurch.roundleafcafe.order.domain.Orders;
import com.ypdchurch.roundleafcafe.order.repository.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Orders orderMenu(Orders orders) {
        log.info("orders.orderMenu = {}", orders);
        return ordersRepository.save(orders);
    }
}
