package com.example.coordination.common.exception;

import org.springframework.http.HttpStatus;

public class NoBrandPriceException extends ServiceException {
    public NoBrandPriceException() {
        super(HttpStatus.NOT_FOUND.value(), "No brand price");
    }
}
