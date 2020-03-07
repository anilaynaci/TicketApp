package com.example.ticketapp.controller;

import com.example.ticketapp.entity.Flight;
import com.example.ticketapp.repository.FlightRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FlightControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private FlightRepository flightRepository;

    @Test
    public void save_startDate_is_before_currentDate_400() throws JSONException {

        String flightInJson = "{ \"name\": \"Flight 5\", \"startDate\":\"1580593304444\", \"endDate\":\"1580594304444\", \"price\":50.50, \"quota\":30, \"company\":{ \"id\":44 }, \"airport\":{ \"id\":42 }, \"route\":{ \"id\":43 } }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(flightInJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/flight/add", entity, String.class);

        printJSON(response);

        String expectedJson = "{\"error\": \"start date must be greater than current date\"}";
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        verify(flightRepository, times(0)).save(any(Flight.class));

    }

    @Test
    public void save_endDate_is_before_startDate_400() throws JSONException {

        String flightInJson = "{ \"name\": \"Flight 5\", \"startDate\":\"1589593304444\", \"endDate\":\"1589493304444\", \"price\":50.50, \"quota\":30, \"company\":{ \"id\":44 }, \"airport\":{ \"id\":42 }, \"route\":{ \"id\":43 } }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(flightInJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/flight/add", entity, String.class);

        printJSON(response);

        String expectedJson = "{\"error\": \"end date must be greater than start date\"}";
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        verify(flightRepository, times(0)).save(any(Flight.class));

    }

    @Test
    public void save_route_not_found_400() throws JSONException {

        String flightInJson = "{ \"name\": \"Flight 5\", \"startDate\":\"1589593304444\", \"endDate\":\"1589693304444\", \"price\":50.50, \"quota\":30, \"company\":{ \"id\":44 }, \"airport\":{ \"id\":42 }, \"route\":{ \"id\":100 } }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(flightInJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/flight/add", entity, String.class);

        printJSON(response);

        String expectedJson = "{\"error\": \"route not found\"}";
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        verify(flightRepository, times(0)).save(any(Flight.class));

    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}