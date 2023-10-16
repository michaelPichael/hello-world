package com.example.helloworld.repository;

import com.example.helloworld.domain.Aminal;
import com.example.helloworld.mapper.AminalMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class AminalRepository {
    private JdbcTemplate jdbcTemplate;
    public void insertAminal(Aminal aminal){
        jdbcTemplate.update("INSERT INTO AMINALS VALUES(?, ?, ?, ?);", aminal.getName(), aminal.getAverageLifeSpan(), aminal.getNumberLegs(), aminal.getHabitat().toString());
    }

    public Aminal getAminal(String aminalName){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM AMINALS WHERE NAME = ?;", new AminalMapper(), aminalName);
        } catch(IncorrectResultSizeDataAccessException e){
            if(e.getActualSize() > 1){
                log.error("Too many rows from result set");
                throw e;
            } else {
                log.error("No aminal found with that name");
                return null;
            }
        }
    }
}
