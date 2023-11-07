package com.example.helloworld.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void handleServerErrorWithBadRequest() {
        // Given
        WeatherAPIException weatherAPIException = new WeatherAPIException("Client Error Exception While Getting Grid Data", new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        ResponseEntity<Error> expectedResponse = ResponseEntity.badRequest().body(new Error(weatherAPIException.getMessage()));

        // When
        ResponseEntity<Error> res = globalExceptionHandler.handleServerError(weatherAPIException);

        // Then
        assertEquals(expectedResponse, res);
    }

    @Test
    public void handleServerErrorWithInternalServerError() {
        // Given
        WeatherAPIException weatherAPIException = new WeatherAPIException("Server Error Exception While Getting Grid Data", new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<Error> expectedResponse = ResponseEntity.internalServerError().body(new Error(weatherAPIException.getMessage()));

        // When
        ResponseEntity<Error> res = globalExceptionHandler.handleServerError(weatherAPIException);

        // Then
        assertEquals(expectedResponse, res);
    }

    @Test
    public void handleGeneralErrorTest() {
        // Given
        Exception exception = new Exception();
        ResponseEntity<Error> expectedResponse = ResponseEntity.internalServerError().body(new Error("banana"));

        // When
        ResponseEntity<Error> res = globalExceptionHandler.handleGeneralError(exception);

        // Then
        assertEquals(expectedResponse, res);
    }
}