package com.example.helloworld.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Error> handleServerError(WeatherAPIException e) {
        log.error("Weather API Error", e);
        Error error = new Error(e.getMessage());
        if (e.getStatusCode().is4xxClientError()) {
            return ResponseEntity.badRequest().body(error);
        } else {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleGeneralError(Exception e) {
        log.error("general error", e);
        Error error = new Error("banana");
        return ResponseEntity.internalServerError().body(error);
    }
}
