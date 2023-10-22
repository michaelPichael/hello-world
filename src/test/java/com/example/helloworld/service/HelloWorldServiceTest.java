package com.example.helloworld.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldServiceTest {
    HelloWorldService helloWorldService = new HelloWorldService();
    @Test
    public void sayHelloTest(){
        // Given
        String name = "Volea";
        String expectedResult = "Hello, Volea!";

        // When
        String res = helloWorldService.sayHello(name);

        // Then
        assertEquals(expectedResult, res);
    }

    @Test
    public void sayHelloGivenNull(){
        // Given
        String expectedResult = "Hello, Vole!";

        // When
        String res = helloWorldService.sayHello(null);

        // Then
        assertEquals(expectedResult, res);
    }
}