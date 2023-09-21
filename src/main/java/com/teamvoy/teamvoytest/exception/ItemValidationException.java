package com.teamvoy.teamvoytest.exception;

import org.springframework.http.HttpStatus;

public class ItemValidationException extends HttpErrorException{
    public ItemValidationException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY.value(), message);
    }
}
