package com.example.coordination.common.exception;

public class DuplicateBrandException extends RuntimeException {
    public DuplicateBrandException(String name) {
        super(name + " already exists");
    }
}
