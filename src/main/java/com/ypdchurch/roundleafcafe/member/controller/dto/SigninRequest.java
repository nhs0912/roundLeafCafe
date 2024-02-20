package com.ypdchurch.roundleafcafe.member.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SigninRequest {
    private String email;
    private String password;
}
