package com.example.coordination.common.advice;

import com.example.coordination.common.dto.ErrorDto;
import com.example.coordination.common.exception.CategoryFoundException;
import com.example.coordination.common.exception.NoBrandPriceException;
import com.example.coordination.common.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<ErrorDto> serviceException(ServiceException e){
        ErrorDto errorResult = new ErrorDto(e.getCode(), e.getMessage());
        return ResponseEntity.ok().body(errorResult);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> exception(Exception e) {
        ErrorDto errorResult = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.internalServerError().body(errorResult);
    }
}
