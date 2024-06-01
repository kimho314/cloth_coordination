package com.example.coordination.common.exception;

public class NoPriceException extends RuntimeException {
    public NoPriceException() {
        super("no price found");
    }
}
