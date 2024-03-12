package com.ypdchurch.roundleafcafe.token.enums;

import lombok.Getter;

@Getter
public enum TokenStatus {
    ACTIVE("01"), TERMINATED("02");
    private String code;

    TokenStatus(String code) {
        this.code = code;
    }
}
