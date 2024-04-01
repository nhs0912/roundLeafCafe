package com.ypdchurch.roundleafcafe.menu.enums;

import lombok.Getter;

@Getter
public enum MenuOptionStatus {

    SHOW("01"), HIDE("02");

    private final String value;

    MenuOptionStatus(final String value) {
        this.value = value;
    }
}
