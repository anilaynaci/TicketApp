package com.example.ticketapp.controller;

import com.example.ticketapp.entity.Flight;
import com.example.ticketapp.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/flight")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    @PostMapping(path="/add")
    public ResponseEntity<Object> addNewFlight (@Valid @RequestBody Flight flight) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (now.compareTo(flight.getStartDate()) >= 0) {
            Map<String, String> body = new LinkedHashMap<>();
            body.put("error", "start date must be greater than current date");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        if (flight.getStartDate().compareTo(flight.getEndDate()) >= 0) {
            Map<String, String> body = new LinkedHashMap<>();
            body.put("error", "end date must be greater than start date");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        flightRepository.save(flight);

        return new ResponseEntity<>(flight, null, HttpStatus.OK);
    }

    @GetMapping(path = "/findBy")
    public ResponseEntity<Object> findFlight(
            @RequestParam(value = "airportName",  required = false) String airportName,
            @RequestParam(value = "companyName",  required = false) String companyName,
            @RequestParam(value = "routeStart",  required = false) String routeStart,
            @RequestParam(value = "routeDestination",  required = false) String routeDestination) {

        List<Flight> flights = flightRepository.findFlight(airportName, companyName, routeStart, routeDestination);

        return new ResponseEntity<>(flights, null, HttpStatus.OK);
    }
}