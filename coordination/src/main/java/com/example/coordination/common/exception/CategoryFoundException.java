package com.example.coordination.common.exception;

public class CategoryFoundException extends RuntimeException {
    public CategoryFoundException(String name) {
        super(name + " found");
    }
}
