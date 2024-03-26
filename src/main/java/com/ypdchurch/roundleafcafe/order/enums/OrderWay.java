package com.ypdchurch.roundleafcafe.order.enums;

import lombok.Getter;

@Getter
public enum OrderWay {

    ONLINE("01"),
    OFFLINE("02");

    private String value;

    OrderWay(String value) {
        this.value = value;
    }
}
