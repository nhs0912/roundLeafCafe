package com.ypdchurch.roundleafcafe.order.enums;

public enum OrderStatus {
    ORDER_ACCEPTED("01"), ORDER_WAITING("02"), COOKING("03"), MENU_ALREADY("04"), PICK_UP_COMPLETE("05"), WHOLE_COMPLETE("06"), CANCLE("90");

    private String code;

    OrderStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
