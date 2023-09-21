package com.teamvoy.teamvoytest.exception.handler;

import com.teamvoy.teamvoytest.exception.HttpErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = HttpErrorException.class)
    public ResponseEntity<Object> handleHttpException(HttpErrorException exception) {
        return buildExceptionBody(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
    }

    private ResponseEntity<Object> buildExceptionBody(String message, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(httpStatus.value());
        exceptionResponse.setMessage(message);
        return ResponseEntity
                .status(httpStatus)
                .body(exceptionResponse);
    }
}
