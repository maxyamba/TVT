package com.teamvoy.teamvoytest.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Accessors(chain = true)
public class HttpErrorException extends RuntimeException {
    private final int code;

    @Setter
    private Map<String, String> wrongFields;

    @Setter
    private Map<String, String> messageArgs;

    public HttpErrorException(int code, String message) {
        super(message);
        this.code = code;
    }
}
