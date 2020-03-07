package com.example.ticketapp.controller;

import com.example.ticketapp.entity.Ticket;
import com.example.ticketapp.repository.TicketRepository;
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
public class TicketControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    public void save_flight_start_date_is_before_current_date_400() throws JSONException {

        String ticketInJson = "{ \"user\": { \"name\": \"anil\", \"surname\": \"aynaci\", \"email\": \"anil.aynaci@hotmail.com\" }, \"flight\": { \"id\": 11 } }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(ticketInJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/ticket/add", entity, String.class);

        printJSON(response);

        String expectedJson = "{\"error\":\"flight start date must be greater than current date\"}";
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        verify(ticketRepository, times(0)).save(any(Ticket.class));

    }

    @Test
    public void save_flight_not_found_400() throws JSONException {

        String ticketInJson = "{ \"user\": { \"name\": \"anil\", \"surname\": \"aynaci\", \"email\": \"anil.aynaci@hotmail.com\" }, \"flight\": { \"id\": 100 } }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(ticketInJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/ticket/add", entity, String.class);

        printJSON(response);

        String expectedJson = "{\"error\":\"flight not found\"}";
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        verify(ticketRepository, times(0)).save(any(Ticket.class));

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