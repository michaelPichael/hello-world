package com.example.helloworld.controller;

import com.example.helloworld.domain.Aminal;
import com.example.helloworld.service.AminalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AminalFactControllerTest {
    AminalService aminalService = Mockito.mock(AminalService.class);
    AminalFactController aminalFactController = new AminalFactController(aminalService);

    @Test
    public void createAminalTest(){
        // given
        Aminal aminal = Mockito.mock(Aminal.class);

        // when
        ResponseEntity<Void> res = aminalFactController.createAminal(aminal);

        // then
        verify(aminalService, times(1)).createAminal(aminal);

        // and
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNull(res.getBody());
    }

    @Test
    public void getAminalWhereAminalExists() {
        // given
        String name = "Vole";
        Aminal aminal = Mockito.mock(Aminal.class);
        Mockito.when(aminalService.getAminal(name)).thenReturn(aminal);

        // when
        ResponseEntity<Aminal> res = aminalFactController.getAminal(name);

        // then
        verify(aminalService, times(1)).getAminal(name);

        // and
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(aminal, res.getBody());
    }

    @Test
    public void getAminalWhereAminalDoesNotExists() {
        // given
        String name = "Butts";
        Mockito.when(aminalService.getAminal(name)).thenReturn(null);

        // when
        ResponseEntity<Aminal> res = aminalFactController.getAminal(name);

        // then
        verify(aminalService, times(1)).getAminal(name);

        // and
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNull(res.getBody());
    }
}