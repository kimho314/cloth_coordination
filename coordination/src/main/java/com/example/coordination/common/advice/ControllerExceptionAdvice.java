package com.example.coordination.common.advice;

import com.example.coordination.common.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> exception(Exception e) {
        ErrorDto errorResult = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.internalServerError().body(errorResult);
    }
}
