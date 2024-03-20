package com.ypdchurch.roundleafcafe.common.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomJwtSecurityContextFactory.class)
public @interface WithMockJwtUser {
    String userName() default "user";

    String role() default "USER";
}
