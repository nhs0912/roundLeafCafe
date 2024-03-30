package com.ypdchurch.roundleafcafe.menu.enums;

import lombok.Getter;

@Getter
public enum CategoryStatus {
    SHOW("01"), HIDE("02");

    private String value;

    CategoryStatus(String value) {
        this.value = value;
    }
}
