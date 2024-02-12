package com.ypdchurch.roundleafcafe.order.repository;

import com.ypdchurch.roundleafcafe.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
