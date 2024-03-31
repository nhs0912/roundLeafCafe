package com.ypdchurch.roundleafcafe.menu.exception;

import com.ypdchurch.roundleafcafe.menu.exception.code.MenuOptionErrorCode;

public class MenuOptionCustomException extends RuntimeException {
    private MenuOptionErrorCode menuOptionErrorCode;

    public MenuOptionCustomException(String message) {
        super(message);
    }

    public MenuOptionCustomException(MenuOptionErrorCode menuOptionErrorCode) {
        super(menuOptionErrorCode.getMessage());
        this.menuOptionErrorCode = menuOptionErrorCode;
    }
}
