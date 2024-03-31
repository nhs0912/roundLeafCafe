package com.ypdchurch.roundleafcafe.menu.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuOptionErrorCode {

    MENU_OPTION_NOT_FOUND("Menu Option을 찾을 수 없습니다.");

    private final String message;

}
