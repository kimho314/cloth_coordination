package com.example.coordination.common.exception;

public class NoCategoryException extends RuntimeException {
    public NoCategoryException() {
        super("category not found");
    }
}
