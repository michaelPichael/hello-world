package com.example.helloworld.service;

import com.example.helloworld.domain.Forecast;
import com.example.helloworld.http.WeatherHttpClient;
import com.example.helloworld.http.domain.Grid;
import com.example.helloworld.http.domain.Temperature;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ForecastService {
    private WeatherHttpClient weatherHttpClient;
    public Optional<Forecast> getForecast(double latitude, double longitude){
        Optional<Grid> grid = weatherHttpClient.getGrid(latitude, longitude);
        if (grid.isEmpty()) {
            return Optional.empty();
        }
        Optional<Temperature> temperature = weatherHttpClient.getTemperature(grid.get().getProperties().getGridX(), grid.get().getProperties().getGridX());
        if (temperature.isEmpty()) {
            return Optional.empty();
        }
        Temperature.Properties.Period tonightsPeriod = temperature.get().getProperties().getPeriods().get(0);
        return Optional.of(new Forecast(tonightsPeriod.getTemperature().toString() + tonightsPeriod.getTemperatureUnit()));
    }
}
