package com.ypdchurch.roundleafcafe.talent.enums;

import lombok.Getter;

@Getter
public enum TransferResult {
    SUCCESS("01"), FAIL("02");

    private String value;

    TransferResult(String value) {
        this.value = value;
    }
}
