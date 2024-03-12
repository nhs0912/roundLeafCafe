package com.ypdchurch.roundleafcafe.common.exception;

import com.ypdchurch.roundleafcafe.common.exception.code.MemberErrorCode;
import lombok.Getter;

@Getter
public class MemberCustomException extends RuntimeException {
    private MemberErrorCode memberErrorCode;

    public MemberCustomException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }

    public MemberCustomException(String message) {
        super(message);
    }

}
