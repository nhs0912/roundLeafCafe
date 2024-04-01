package com.ypdchurch.roundleafcafe.menu.enums;

import lombok.Getter;

@Getter
public enum MenuStatus {
    SHOW("01"),
    HIDE("02");

    private final String value;

    MenuStatus(final String value) {
        this.value = value;
    }
}
