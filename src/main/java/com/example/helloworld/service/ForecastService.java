package com.example.helloworld.service;

import com.example.helloworld.domain.Forecast;
import com.example.helloworld.http.WeatherHttpClient;
import com.example.helloworld.http.domain.Grid;
import com.example.helloworld.http.domain.Temperature;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ForecastService {
    private WeatherHttpClient weatherHttpClient;
    public Forecast getForecast(double latitude, double longitude){
        Grid grid = weatherHttpClient.getGrid(latitude, longitude);
        Temperature temperature = weatherHttpClient.getTemperature(grid.getProperties().getGridX(), grid.getProperties().getGridX());
        Temperature.Properties.Period tonightsPeriod = temperature.getProperties().getPeriods().get(0);
        return new Forecast(tonightsPeriod.getTemperatureValue().toString() + tonightsPeriod.getTemperatureUnit());
    }
}
