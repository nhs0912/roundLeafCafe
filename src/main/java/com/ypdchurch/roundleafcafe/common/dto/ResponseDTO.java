package com.ypdchurch.roundleafcafe.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDTO<T> {
    private static final String SUCCEESS = "성공";
    private Integer status;
    private String message;
    private T data;

    public ResponseDTO() {
        this.status = HttpStatus.OK.value();
        this.message = SUCCEESS;
    }

    public ResponseDTO(T data) {
        this();
        this.data = data; //응답할 데이터 바디
    }

    public ResponseDTO(HttpStatus httpStatus, String message, T data) {
        this.status = httpStatus.value();
        this.message = message; //에러 제목
        this.data = data; //에러 내용
    }
}
