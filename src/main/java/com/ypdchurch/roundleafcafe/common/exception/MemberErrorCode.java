package com.ypdchurch.roundleafcafe.common.exception;

import lombok.Getter;

@Getter
public enum MemberErrorCode {
    ID_AND_PASSWORD_IS_WRONG("이메일 또는 비밀번호가 잘못되었습니다."),
    MEMBER_NOT_FOUND("찾는 멤버가 없습니다."),
    ALREADY_EXIST_EMAIL("이미 등록된 이메일이 있습니다.");

    private final String message;

    MemberErrorCode(String message) {
        this.message = message;
    }
}
