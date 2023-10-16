package com.example.helloworld.service;

import com.example.helloworld.domain.Aminal;
import com.example.helloworld.repository.AminalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@AllArgsConstructor
public class AminalService {
    private AminalRepository aminalRepository;
    public void createAminal(Aminal aminal){
        log.info(aminal.toString());
        aminalRepository.insertAminal(aminal);
    }

    public Aminal getAminal(String aminalName){
        log.info(aminalName);
        return aminalRepository.getAminal(aminalName);
    }
}
