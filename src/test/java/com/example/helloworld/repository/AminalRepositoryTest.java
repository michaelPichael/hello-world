package com.example.helloworld.repository;

import com.example.helloworld.domain.Aminal;
import com.example.helloworld.domain.Habitat;
import com.example.helloworld.mapper.AminalMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.example.helloworld.domain.Habitat.BOX;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AminalRepositoryTest {
    String selectAminalQuery = "SELECT * FROM AMINALS WHERE NAME = ?;";
    JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    AminalRepository aminalRepository = new AminalRepository(jdbcTemplate);

    @Test
    public void insertAminalTest(){
        // given
        String query = "INSERT INTO AMINALS VALUES(?, ?, ?, ?);";
        String aminalName = "Pupper";
        int averageLifeSpan = 100000;
        int numberLegs = 4;
        Habitat habitat =  BOX;
        Aminal aminal = new Aminal(aminalName, averageLifeSpan, numberLegs, habitat);

        // when
        aminalRepository.insertAminal(aminal);

        // then
        verify(jdbcTemplate, times(1)).update(query, aminalName, averageLifeSpan, numberLegs, habitat.toString());
    }

    @Test
    public void getAminalWhenReturnedOneAminal(){
        // given
        String aminalName = "Pupper";
        Aminal aminal = Mockito.mock(Aminal.class);
        Mockito.when(jdbcTemplate.queryForObject(eq(selectAminalQuery), any(AminalMapper.class), eq(aminalName))).thenReturn(aminal);

        // when
        Aminal res = aminalRepository.getAminal(aminalName);

        // then
        verify(jdbcTemplate, times(1)).queryForObject(eq(selectAminalQuery), any(AminalMapper.class), eq(aminalName));

        // and
        assertEquals(aminal, res);
    }

    @Test
    public void getAminalWhenReturnedMultipleAminals(){
        // given
        String aminalName = "Butts";
        Mockito.when(jdbcTemplate.queryForObject(eq(selectAminalQuery), any(AminalMapper.class), eq(aminalName))).thenThrow(new IncorrectResultSizeDataAccessException(1, 2));

        // when
        assertThrows(IncorrectResultSizeDataAccessException.class, () -> aminalRepository.getAminal(aminalName));

        // then
        verify(jdbcTemplate, times(1)).queryForObject(eq(selectAminalQuery), any(AminalMapper.class), eq(aminalName));
    }

    @Test
    public void getAminalWhenReturnedNoAminals(){
        // given
        String aminalName = "Butts";
        Mockito.when(jdbcTemplate.queryForObject(eq(selectAminalQuery), any(AminalMapper.class), eq(aminalName))).thenThrow(new IncorrectResultSizeDataAccessException(1, 0));

        // when
        Aminal res = aminalRepository.getAminal(aminalName);

        // then
        verify(jdbcTemplate, times(1)).queryForObject(eq(selectAminalQuery), any(AminalMapper.class), eq(aminalName));

        // and
        assertNull(res);
    }
}