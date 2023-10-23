package com.example.helloworld.controller;

import com.example.helloworld.domain.Forecast;
import com.example.helloworld.service.ForecastService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherForecastController {
    private ForecastService forecastService;

    @GetMapping("/forecast")
    public ResponseEntity<Forecast> getForecast(@RequestParam double latitude, @RequestParam double longitude) {
        Forecast forecast = forecastService.getForecast(latitude, longitude);
        return ResponseEntity.ok(forecast);
    }
}
