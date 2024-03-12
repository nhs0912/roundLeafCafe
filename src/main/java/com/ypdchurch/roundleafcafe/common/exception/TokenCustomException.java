package com.ypdchurch.roundleafcafe.common.exception;

import com.ypdchurch.roundleafcafe.common.exception.code.TokenErrorCode;
import lombok.Getter;

@Getter
public class TokenCustomException extends RuntimeException {

    private TokenErrorCode tokenErrorCode;

    public TokenCustomException(TokenErrorCode tokenErrorCode) {
        super(tokenErrorCode.getMessage());
        this.tokenErrorCode = tokenErrorCode;
    }

    public TokenCustomException(String message) {
        super(message);
    }

}
