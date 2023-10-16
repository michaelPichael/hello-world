package com.example.helloworld.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthControllerTest {
    HealthController healthController = new HealthController();
    @Test
    public void checkHealthTest(){
        // Given
        String expectedResult = "The Endpoint is Up! :)";
        // When
        String res = healthController.checkHealth();

        // Then
        assertEquals(expectedResult, res);
    }
}