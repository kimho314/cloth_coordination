package com.example.coordination.common.exception;

import org.springframework.http.HttpStatus;

public class CategoryFoundException extends ServiceException {
    public CategoryFoundException(String name) {
        super(HttpStatus.BAD_REQUEST.value(), name + " found");
    }
}
