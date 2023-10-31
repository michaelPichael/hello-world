package com.example.helloworld.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

@Getter
public class WeatherAPIException extends RuntimeException {
    private final HttpStatus statusCode;
    public WeatherAPIException(String message, HttpStatusCodeException cause) {
        super(message, cause);
        this.statusCode = cause.getStatusCode();
    }
}
