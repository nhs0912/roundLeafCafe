package com.ypdchurch.roundleafcafe.order.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    CASH("01"),
    TALENT("02"),
    TRANSFER_ACCOUNT("03");

    private String value;

    PaymentMethod(String value) {
        this.value = value;
    }

}
