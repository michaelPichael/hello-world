package com.example.helloworld.mapper;

import com.example.helloworld.domain.Aminal;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.helloworld.domain.Habitat.PRAIRIE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AminalMapperTest {

    AminalMapper aminalMapper = new AminalMapper();

    @Test
    public void mapRowTest() throws SQLException {
        // given
        Aminal expectedAminal = new Aminal("Vole", 1000000, 2, PRAIRIE);

        // and
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getString("NAME")).thenReturn("Vole");
        Mockito.when(resultSet.getInt("AVERAGE_LIFE_SPAN")).thenReturn(1000000);
        Mockito.when(resultSet.getInt("NUMBER_LEGS")).thenReturn(2);
        Mockito.when(resultSet.getString("HABITAT")).thenReturn("PRAIRIE");

        // when
        Aminal res = aminalMapper.mapRow(resultSet, 1);

        // then
        assertEquals(expectedAminal, res);
    }

    @Test
    public void setNameThrowsException() throws SQLException {
        // given
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getString("NAME")).thenThrow(SQLException.class);

        try {
            // when
            aminalMapper.mapRow(resultSet, 1);

        } catch (RuntimeException e) {
            // then
            assertEquals("Unable to parse Aminal result set", e.getMessage());
        }
    }

    @Test
    public void setAverageLifeSpanThrowsException() throws SQLException {
        // given
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getString("NAME")).thenReturn("Kitty");
        Mockito.when(resultSet.getInt("AVERAGE_LIFE_SPAN")).thenThrow(SQLException.class);

        try {
            // when
            aminalMapper.mapRow(resultSet, 1);

        } catch (RuntimeException e) {
            // then
            assertEquals("Unable to parse Aminal result set", e.getMessage());
        }
    }

    @Test
    public void setNumberLegsThrowsException() throws SQLException {
        // given
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getString("NAME")).thenReturn("Kitty");
        Mockito.when(resultSet.getInt("AVERAGE_LIFE_SPAN")).thenReturn(999);
        Mockito.when(resultSet.getInt("NUMBER_LEGS")).thenThrow(SQLException.class);

        try {
            // when
            aminalMapper.mapRow(resultSet, 1);

        } catch (RuntimeException e) {
            // then
            assertEquals("Unable to parse Aminal result set", e.getMessage());
        }
    }

    @Test
    public void setHabitatThrowsException() throws SQLException {
        // given
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getString("NAME")).thenReturn("Kitty");
        Mockito.when(resultSet.getInt("AVERAGE_LIFE_SPAN")).thenReturn(999);
        Mockito.when(resultSet.getInt("NUMBER_LEGS")).thenReturn(3);
        Mockito.when(resultSet.getString("HABITAT")).thenThrow(SQLException.class);

        try {
            // when
            aminalMapper.mapRow(resultSet, 1);

        } catch (RuntimeException e) {
            // then
            assertEquals("Unable to parse Aminal result set", e.getMessage());
        }
    }
}