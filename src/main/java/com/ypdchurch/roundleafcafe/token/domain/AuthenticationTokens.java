package com.ypdchurch.roundleafcafe.token.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationTokens {
    private String accessToken;
    private String refreshToken;
}
