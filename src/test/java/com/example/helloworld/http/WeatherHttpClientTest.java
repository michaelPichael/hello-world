package com.example.helloworld.http;

import com.example.helloworld.exception.WeatherAPIException;
import com.example.helloworld.http.domain.Grid;
import com.example.helloworld.http.domain.Temperature;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WeatherHttpClientTest {
    String urlDomain = "https://api.weather.gov";
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    WeatherHttpClient weatherHttpClient = new WeatherHttpClient(restTemplate);

    @Test
    public void getGridWhenFound() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        int gridX = 49;
        int gridY = 85;
        Grid expectedGrid = new Grid(new Grid.Properties(gridX, gridY));
        Mockito.when(restTemplate.getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude)).thenReturn(ResponseEntity.of(Optional.of(new Grid(new Grid.Properties(gridX, gridY)))));

        // When
        Optional<Grid> res = weatherHttpClient.getGrid(latitude, longitude);

        // Then
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude);

        // And
        assertTrue(res.isPresent());
        assertEquals(expectedGrid, res.get());
    }

    @Test
    public void getGrid404Error() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        Mockito.when(restTemplate.getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // When
        Optional<Grid> res = weatherHttpClient.getGrid(latitude, longitude);

        // Then
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude);

        // And
        assertTrue(res.isEmpty());
    }

    @Test
    public void getGridGenericClientError() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        String expectedMessage = "Client Error Exception While Getting Grid Data";
        Mockito.when(restTemplate.getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        // When
        WeatherAPIException weatherAPIException = assertThrows(WeatherAPIException.class, () -> weatherHttpClient.getGrid(latitude, longitude));

        // Then
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude);

        // And
        assertEquals(expectedMessage, weatherAPIException.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, weatherAPIException.getStatusCode());
    }

    @Test
    public void getGridGenericServerError() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        String expectedMessage = "Server Error Exception While Getting Grid Data";
        Mockito.when(restTemplate.getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // When
        WeatherAPIException weatherAPIException = assertThrows(WeatherAPIException.class, () -> weatherHttpClient.getGrid(latitude, longitude));

        // Then
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude);

        // And
        assertEquals(expectedMessage, weatherAPIException.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, weatherAPIException.getStatusCode());
    }

    @Test
    public void getTemperatureWhenFound() {
        // Given
        int temperature = 51;
        String temperatureUnit = "F";
        int gridX = 49;
        int gridY = 85;
        Temperature expectedTemperature = new Temperature(new Temperature.Properties(List.of(new Temperature.Properties.Period(temperature, temperatureUnit))));
        Mockito.when(restTemplate.getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY)).thenReturn(ResponseEntity.of(Optional.of(new Temperature(new Temperature.Properties(List.of(new Temperature.Properties.Period(temperature, temperatureUnit)))))));

        // When
        Optional<Temperature> res = weatherHttpClient.getTemperature(gridX, gridY);

        // Then
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY);

        // And
        assertTrue(res.isPresent());
        assertEquals(expectedTemperature, res.get());
    }

    @Test
    public void getTemperature404Error() {
        // Given
        int gridX = 49;
        int gridY = 85;
        Mockito.when(restTemplate.getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // When
        Optional<Temperature> res = weatherHttpClient.getTemperature(gridX, gridY);

        // Then
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY);

        // And
        assertTrue(res.isEmpty());
    }

    @Test
    public void getTemperatureGenericClientError() {
        // Given
        int gridX = 49;
        int gridY = 85;
        String expectedMessage = "Client Error Exception While Getting Temperature Data";
        Mockito.when(restTemplate.getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        // When
        WeatherAPIException weatherAPIException = assertThrows(WeatherAPIException.class, () -> weatherHttpClient.getTemperature(gridX, gridY));

        // Then
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY);

        // And
        assertEquals(expectedMessage, weatherAPIException.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, weatherAPIException.getStatusCode());
    }

    @Test
    public void getTemperatureGenericServerError() {
        // Given
        int gridX = 49;
        int gridY = 85;
        String expectedMessage = "Server Error Exception While Getting Temperature Data";
        Mockito.when(restTemplate.getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // When
        WeatherAPIException weatherAPIException = assertThrows(WeatherAPIException.class, () -> weatherHttpClient.getTemperature(gridX, gridY));

        // Then
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY);

        // And
        assertEquals(expectedMessage, weatherAPIException.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, weatherAPIException.getStatusCode());
    }
}