package com.ypdchurch.roundleafcafe.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class SigninResponse {
    private Long id;
    private String accessToken;
    private String refreshToken;
}
