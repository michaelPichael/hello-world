package com.example.helloworld.http;

import com.example.helloworld.http.domain.Grid;
import com.example.helloworld.http.domain.Temperature;
import com.example.helloworld.http.domain.Temperature.Properties;
import com.example.helloworld.http.domain.Temperature.Properties.Period;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeatherHttpClient {

    public Grid getGrid(double latitude, double longitude) {
        return new Grid(new Grid.Properties(49,85));
    }

    public Temperature getTemperature(int gridX, int gridY) {
        return new Temperature(
                new Properties(
                        List.of(
                                new Period(60, "F")
                        )
                )
        );
    }
}
