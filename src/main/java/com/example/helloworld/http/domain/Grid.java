package com.example.helloworld.http.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grid {
    private Properties properties;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private int gridX;
        private int gridY;
    }
}
