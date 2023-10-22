package com.example.helloworld.it;

import com.example.helloworld.HelloWorldApplication;
import com.example.helloworld.domain.Aminal;
import com.example.helloworld.domain.Habitat;
import com.example.helloworld.mapper.AminalMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URI;
import java.net.URISyntaxException;

import static com.example.helloworld.domain.Habitat.MEADOW;
import static com.example.helloworld.domain.Habitat.PRAIRIE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = HelloWorldApplication.class)
public class AminalFactsIT {

    TestRestTemplate testRestTemplate = new TestRestTemplate();
    @Autowired
    JdbcTemplate jdbcTemplate;

    @AfterEach
    public void setUp() {
        String sqlStatement = "DELETE FROM AMINALS;";
        jdbcTemplate.execute(sqlStatement);
    }

    @Test
    public void testAminalFactsLookup() throws URISyntaxException {
        // Given
        URI uri = new URI("http://localhost:8080/aminal?aminalName=Vole");
        // When
        ResponseEntity<Aminal> res = testRestTemplate.getForEntity(uri, Aminal.class);
        // Then
        assertNotNull(res);
        assertEquals(404, res.getStatusCodeValue());
        assertNull(res.getBody());
    }

    @Test
    public void getAminalFound() throws URISyntaxException {
        // Given
        String sqlStatement = "INSERT INTO AMINALS(NAME, AVERAGE_LIFE_SPAN, NUMBER_LEGS, HABITAT) VALUES('MeadowVole', 1000000, 1000, 'MEADOW');";
        jdbcTemplate.execute(sqlStatement);
        URI uri = new URI("http://localhost:8080/aminal?aminalName=MeadowVole");
        Aminal expectedAminal = new Aminal("MeadowVole", 1000000, 1000, MEADOW);
        // When
        ResponseEntity<Aminal> res = testRestTemplate.getForEntity(uri, Aminal.class);
        // Then
        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());
        assertEquals(expectedAminal, res.getBody());
    }

    @Test
    public void createAminalTest() throws URISyntaxException {
        // Given
        String aminalName = "Vole";
        int averageLifeSpan = 10000000;
        int numberLegs = 2;
        Habitat habitat = PRAIRIE;
        URI uri = new URI("http://localhost:8080/aminal");
        Aminal requestObject = new Aminal(aminalName, averageLifeSpan, numberLegs, habitat);
        // When
        ResponseEntity<Void> res = testRestTemplate.postForEntity(uri, requestObject, Void.class);
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM AMINALS WHERE NAME = ?;", aminalName);
        rowSet.first();
        // Then
        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());
        assertNull(res.getBody());
        // And
        assertEquals(aminalName, rowSet.getString("NAME"));
        assertEquals(averageLifeSpan, rowSet.getInt("AVERAGE_LIFE_SPAN"));
        assertEquals(numberLegs, rowSet.getInt("NUMBER_LEGS"));
        assertEquals(habitat.toString(), rowSet.getString("HABITAT"));
    }
}
