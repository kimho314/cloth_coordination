package com.example.coordination.common.exception;

public class NoBrandPriceException extends RuntimeException {
    public NoBrandPriceException() {
        super("No brand price");
    }
}
