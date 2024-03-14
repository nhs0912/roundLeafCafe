package com.ypdchurch.roundleafcafe.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
@Configuration
public class JwtConfig {
    @Value("${custom.jwt.secretKey}")
    private String secretKey;

}
