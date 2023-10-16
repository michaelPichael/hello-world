package com.example.helloworld.service;

import com.example.helloworld.domain.Aminal;
import com.example.helloworld.repository.AminalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AminalServiceTest {
    AminalRepository aminalRepository = Mockito.mock(AminalRepository.class);
    AminalService aminalService = new AminalService(aminalRepository);

    @Test
    public void createAminalTest(){
        // given
        Aminal aminal = Mockito.mock(Aminal.class);

        // when
        aminalService.createAminal(aminal);

        // then
        verify(aminalRepository, times(1)).insertAminal(aminal);
    }

    @Test
    public void getAminalTest(){
        // given
        String aminalName = "Vole";
        Aminal aminal = Mockito.mock(Aminal.class);
        Mockito.when(aminalRepository.getAminal(aminalName)).thenReturn(aminal);

        // when
        Aminal res = aminalService.getAminal(aminalName);

        // then
        verify(aminalRepository, times(1)).getAminal(aminalName);

        // and
        assertEquals(aminal, res);
    }
}