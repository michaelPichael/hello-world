package com.example.helloworld.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {
    public String sayHello(String name) {
        return (name != null) ? "Hello, " + name + "!" : "Hello, Vole!";
    }
}
