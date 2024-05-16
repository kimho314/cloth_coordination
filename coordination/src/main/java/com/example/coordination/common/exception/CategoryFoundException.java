package com.example.coordination.common.exception;

public class CategoryFoundException extends RuntimeException {
    public CategoryFoundException(String message) {
        super(message + " found");
    }
}
