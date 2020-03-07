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

@Controller
@RequestMapping(path="/flight")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    @PostMapping(path="/add")
    public ResponseEntity<Object> addNewFlight (@Valid @RequestBody Flight flight) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (now.compareTo(flight.getStartDate()) >= 0) {
            return new ResponseEntity<>("start date must be greater than current date", HttpStatus.BAD_REQUEST);
        }

        if (flight.getStartDate().compareTo(flight.getEndDate()) >= 0) {
            return new ResponseEntity<>("end date must be greater than start date", HttpStatus.BAD_REQUEST);
        }

        flightRepository.save(flight);

        return new ResponseEntity<>(flight, null, HttpStatus.OK);
    }
}