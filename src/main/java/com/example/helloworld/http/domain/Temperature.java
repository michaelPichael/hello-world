package com.example.helloworld.http.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Temperature {
    private Properties properties;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private List<Period> periods;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Period {
            private Integer temperature;
            private String temperatureUnit;
        }
    }
}
