package com.ypdchurch.roundleafcafe.menu.enums;

import lombok.Getter;

@Getter
public enum CategoryStatus {
    SHOW("01"), HIDE("02");

    private final String value;

    CategoryStatus(final String value) {
        this.value = value;
    }
}
