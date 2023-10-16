package com.example.helloworld.controller;

import com.example.helloworld.domain.Aminal;
import com.example.helloworld.service.AminalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AminalFactController {
    private AminalService aminalService;

    @PostMapping("/aminal")
    public ResponseEntity<Void> createAminal(@RequestBody Aminal aminal){
        aminalService.createAminal(aminal);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/aminal")
    public ResponseEntity<Aminal> getAminal(@RequestParam String aminalName){
        log.info("Aminals are Cool!");
        Aminal aminal = aminalService.getAminal(aminalName);
        return (aminal != null)? ResponseEntity.ok(aminal): ResponseEntity.notFound().build();
    }
}
