package com.example.coordination.common.exception;

public class NoBrandException extends RuntimeException {
    public NoBrandException() {
        super("brand not found");
    }

    public NoBrandException(String brandName) {
        super(brandName + "brand not found");
    }
}
