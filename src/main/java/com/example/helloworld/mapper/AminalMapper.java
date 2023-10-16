package com.example.helloworld.mapper;


import com.example.helloworld.domain.Aminal;
import com.example.helloworld.domain.Habitat;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class AminalMapper implements org.springframework.jdbc.core.RowMapper<Aminal> {

    @Override
    public Aminal mapRow(ResultSet resultSet, int rowNum){
        Aminal aminal = new Aminal();
        try {
            aminal.setName(resultSet.getString("NAME"));
            aminal.setAverageLifeSpan(resultSet.getInt("AVERAGE_LIFE_SPAN"));
            aminal.setNumberLegs(resultSet.getInt("NUMBER_LEGS"));
            aminal.setHabitat(Habitat.valueOf(resultSet.getString("HABITAT")));
        } catch (SQLException e) {
            log.error("Unable to parse Aminal result set");
            throw new RuntimeException("Unable to parse Aminal result set");
        }
        return aminal;
    }
}
