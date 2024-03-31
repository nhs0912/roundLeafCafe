package com.ypdchurch.roundleafcafe.menu.enums;

import lombok.Getter;

@Getter
public enum MenuOptionStatus {

    SHOW("01"), HIDE("02");

    private String value;

    MenuOptionStatus(String value) {
        this.value = value;
    }
}
