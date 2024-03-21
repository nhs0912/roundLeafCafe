package com.ypdchurch.roundleafcafe.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode {
    TOKEN_IS_NOT_FOUND("토큰을 찾을 수 없습니다."),
    NEED_TO_LOGIN_AGAIN("토큰이 유효하지 않아 로그인을 다시 해주세요."),
    TOKEN_IS_EXPIRED("토큰이 만료되었습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다.");

    private final String message;
}
