package com.ypdchurch.roundleafcafe.order.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItem_id", updatable = false)
    private Long id;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_option_id")
    private Long menuItemId;

    @Column(name = "price")
    private BigDecimal price;
}
