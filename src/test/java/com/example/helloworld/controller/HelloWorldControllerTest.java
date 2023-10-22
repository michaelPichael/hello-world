package com.example.helloworld.controller;

import com.example.helloworld.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldControllerTest {
    HelloWorldService helloWorldService = Mockito.mock(HelloWorldService.class);
    HelloWorldController helloWorldController = new HelloWorldController(helloWorldService);
    @Test
    public void sayHelloTest(){
        // Given
        String nickname = "Volea";
        String greeting = "Hello, Volea!";
        Mockito.when(helloWorldService.sayHello(nickname)).thenReturn(greeting);

        // When
        String res = helloWorldController.sayHello(nickname);

        // Then
        assertEquals(greeting, res);
    }

}