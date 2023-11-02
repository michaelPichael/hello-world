package com.example.helloworld.service;

import com.example.helloworld.domain.Forecast;
import com.example.helloworld.http.WeatherHttpClient;
import com.example.helloworld.http.domain.Grid;
import com.example.helloworld.http.domain.Temperature;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ForecastServiceTest {

    WeatherHttpClient weatherHttpClient = Mockito.mock(WeatherHttpClient.class);
    ForecastService forecastService = new ForecastService(weatherHttpClient);

    @Test
    public void testGetForecastWhenFound() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        int gridX = 49;
        int gridY = 85;
        Forecast expectedForecast = new Forecast("52F");
        Mockito.when(weatherHttpClient.getGrid(latitude, longitude)).thenReturn(Optional.of(new Grid(new Grid.Properties(gridX,gridY))));
        Mockito.when(weatherHttpClient.getTemperature(gridX, gridY)).thenReturn(Optional.of(new Temperature(new Temperature.Properties(List.of(new Temperature.Properties.Period(52, "F"))))));

        // When
        Optional<Forecast> res = forecastService.getForecast(latitude, longitude);

        // Then
        Mockito.verify(weatherHttpClient, Mockito.times(1)).getGrid(latitude, longitude);
        Mockito.verify(weatherHttpClient, Mockito.times(1)).getTemperature(gridX,gridY);

        // And
        assertTrue(res.isPresent());
        assertEquals(expectedForecast, res.get());
    }

    @Test
    public void testGetForecastWhenGridIsEmpty() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        Mockito.when(weatherHttpClient.getGrid(latitude, longitude)).thenReturn(Optional.empty());

        // When
        Optional<Forecast> res = forecastService.getForecast(latitude, longitude);

        // Then
        Mockito.verify(weatherHttpClient, Mockito.times(1)).getGrid(latitude, longitude);

        // And
        assertTrue(res.isEmpty());
    }

    @Test
    public void testGetForecastWhenTemperatureIsEmpty() {
        // Given
        double latitude = 33.7183;
        double longitude = -84.4483;
        int gridX = 49;
        int gridY = 85;
        Mockito.when(weatherHttpClient.getGrid(latitude, longitude)).thenReturn(Optional.of(new Grid(new Grid.Properties(gridX,gridY))));
        Mockito.when(weatherHttpClient.getTemperature(gridX, gridY)).thenReturn(Optional.empty());

        // When
        Optional<Forecast> res = forecastService.getForecast(latitude, longitude);

        // Then
        Mockito.verify(weatherHttpClient, Mockito.times(1)).getGrid(latitude, longitude);
        Mockito.verify(weatherHttpClient, Mockito.times(1)).getTemperature(gridX,gridY);

        // And
        assertTrue(res.isEmpty());
    }

}