package com.example.coordination.common.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private final Integer code;
    private final String message;

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
