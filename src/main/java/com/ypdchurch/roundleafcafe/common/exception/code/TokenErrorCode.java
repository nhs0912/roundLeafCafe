package com.ypdchurch.roundleafcafe.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode {
    TOKEN_IS_NOT_FOUND("토큰을 찾을 수 없습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다.");

    private final String message;
}
