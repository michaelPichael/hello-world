package com.example.helloworld.http;

import com.example.helloworld.exception.WeatherAPIException;
import com.example.helloworld.http.domain.Grid;
import com.example.helloworld.http.domain.Temperature;
import com.example.helloworld.http.domain.Temperature.Properties;
import com.example.helloworld.http.domain.Temperature.Properties.Period;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class WeatherHttpClient {
    RestTemplate restTemplate = new RestTemplate();
    String urlDomain = "https://api.weather.gov";
    public Optional<Grid> getGrid(double latitude, double longitude) {
        try {
            ResponseEntity<Grid> res = restTemplate.getForEntity(urlDomain + "/points/{latitude},{longitude}", Grid.class, latitude, longitude);
            log.info("Status: {}, Body: {}", res.getStatusCode(), res.getBody());
            return Optional.ofNullable(res.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();
            } else {
                throw new WeatherAPIException("Client Error Exception While Getting Grid Data", e);
            }
        } catch (HttpServerErrorException e) {
            throw new WeatherAPIException("Server Error Exception While Getting Grid Data", e);
        }
    }

    public Optional<Temperature> getTemperature(int gridX, int gridY) {
        try {
            ResponseEntity<Temperature> res = restTemplate.getForEntity(urlDomain + "/gridpoints/FFC/{gridX},{gridY}/forecast", Temperature.class, gridX, gridY);
            log.info("Status: {}, Body: {}", res.getStatusCode(), res.getBody());
            return Optional.ofNullable(res.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();
            } else {
                throw new WeatherAPIException("Client Error Exception While Getting Temperature Data", e);
            }
        } catch (HttpServerErrorException e) {
            throw new WeatherAPIException("Server Error Exception While Getting Temperature Data", e);
        }
    }
}
