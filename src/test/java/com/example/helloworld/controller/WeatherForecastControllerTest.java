package com.example.helloworld.controller;

import com.example.helloworld.domain.Forecast;
import com.example.helloworld.service.ForecastService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WeatherForecastControllerTest {
    ForecastService forecastService = Mockito.mock(ForecastService.class);
    WeatherForecastController weatherForecastController = new WeatherForecastController(forecastService);

    @Test
    public void getForecastFound() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        Mockito.when(forecastService.getForecast(latitude, longitude)).thenReturn(Optional.of(new Forecast("51F")));

        // When
        ResponseEntity<Forecast> res = weatherForecastController.getForecast(latitude, longitude);

        // Then
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(new Forecast("51F"), res.getBody());
    }

    @Test
    public void getForecastNotFound() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        Mockito.when(forecastService.getForecast(latitude, longitude)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Forecast> res = weatherForecastController.getForecast(latitude, longitude);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNull(res.getBody());
    }

    @Test
    public void getForecastBadRequest() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        Mockito.when(forecastService.getForecast(latitude, longitude)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        // When
        ResponseEntity<Forecast> res = weatherForecastController.getForecast(latitude, longitude);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertNull(res.getBody());
    }

    @Test
    public void getForecastInternalServerError() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        Mockito.when(forecastService.getForecast(latitude, longitude)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // When
        ResponseEntity<Forecast> res = weatherForecastController.getForecast(latitude, longitude);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertNull(res.getBody());
    }
}