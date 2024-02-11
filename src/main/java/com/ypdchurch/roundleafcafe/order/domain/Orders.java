package com.ypdchurch.roundleafcafe.order.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id", updatable = false)
    private Long id;

    @Column(name = "member_id", updatable = false)
    private Long memberId;

    @Column(name = "basket_id", updatable = false)
    private Long basketId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "requests")
    private String requests;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
