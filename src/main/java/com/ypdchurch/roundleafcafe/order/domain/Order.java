package com.ypdchurch.roundleafcafe.order.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.order.enums.OrderStatus;
import com.ypdchurch.roundleafcafe.order.enums.OrderWay;
import com.ypdchurch.roundleafcafe.order.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Entity(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false)
    private Long id;

    @NotNull
    @Column(name = "member_id", updatable = false)
    private Long memberId;

    @NotNull
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "menu_option_id")
    private Long menuOptionId;

    @NotNull
    @Min(value = 0, message = "총 가격은 0원 이상이어야합니다. 입력된 금액 : ${totalPrice}")
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "requests")
    private String requests;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod payMentMethod;

    @Column(name = "order_way")
    @Enumerated(EnumType.STRING)
    private OrderWay way;

    public Order accepted() {

        this.status = OrderStatus.ORDER_ACCEPTED;
        return this;
    }

    public Order confirmed() {
        this.status = OrderStatus.ORDER_CONFIRMED;
        return this;
    }

    public Order menuAlready() {
        this.status = OrderStatus.MENU_ALREADY_COMPLETE;
        return this;
    }

    public Order cooking() {
        this.status = OrderStatus.COOKING;
        return this;
    }

    public Order pickup() {
        this.status = OrderStatus.PICK_UP_COMPLETE;
        return this;
    }

    public Order wholeCompete() {
        this.status = OrderStatus.WHOLE_COMPLETE;
        return this;
    }

    public Order cancel() {
        this.status = OrderStatus.CANCEL;
        return this;
    }

    private Order of(Order order) {
        return Order.builder()
                .id(order.getId())
                .memberId(order.getMemberId())
                .status(order.getStatus())
                .requests(order.getRequests())
                .orderItemId(order.getOrderItemId())
                .build();
    }

}
