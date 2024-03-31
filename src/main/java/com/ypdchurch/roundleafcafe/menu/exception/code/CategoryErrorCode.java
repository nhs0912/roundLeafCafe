package com.ypdchurch.roundleafcafe.menu.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode {
    NOT_FOUND_CATEGORY("카테고리가 없습니다."),
    THERE_IS_NOT_EXIST_ID("id가 없습니다.");
    private final String message;
}
