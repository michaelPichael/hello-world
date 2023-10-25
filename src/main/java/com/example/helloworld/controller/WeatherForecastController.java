package com.example.helloworld.controller;

import com.example.helloworld.domain.Forecast;
import com.example.helloworld.service.ForecastService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class WeatherForecastController {
    private ForecastService forecastService;

    @GetMapping("/forecast")
    public ResponseEntity<Forecast> getForecast(@RequestParam double latitude, @RequestParam double longitude) {
        try {
            Optional<Forecast> forecast = forecastService.getForecast(latitude, longitude);
            if (forecast.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(forecast.get());
//        return forecast.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().build();
        } catch (HttpServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}
