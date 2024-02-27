package com.ypdchurch.roundleafcafe.common.exception.handler;

import com.ypdchurch.roundleafcafe.member.controller.MemberController;
import com.ypdchurch.roundleafcafe.common.exception.MemberCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {MemberController.class})
public class MemberControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberCustomException.class)
    public ResponseEntity<String> memberCustomExceptionHandle(MemberCustomException e) {
        log.error("[MemberCustomException] ", e);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
