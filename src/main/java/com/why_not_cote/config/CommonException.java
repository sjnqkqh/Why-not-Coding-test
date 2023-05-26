package com.why_not_cote.config;

import com.why_not_cote.util.code.ApiExceptionCode;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{

    private final ApiExceptionCode errorCode;

    public CommonException(ApiExceptionCode errorCode){
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public CommonException(String message, ApiExceptionCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

}
