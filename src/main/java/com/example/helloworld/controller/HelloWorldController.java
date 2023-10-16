package com.example.helloworld.controller;


import com.example.helloworld.service.HelloWorldService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HelloWorldController {
    HelloWorldService service;

    @GetMapping("/hello")
    public String sayHello(@RequestParam(required = false) String nickname) {
        return service.sayHello(nickname);
    }
}
