package com.example.ticketapp.controller;

import com.example.ticketapp.entity.Airport;
import com.example.ticketapp.entity.Company;
import com.example.ticketapp.entity.Ticket;
import com.example.ticketapp.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/airport")
public class AirportController {
    @Autowired
    private AirportRepository airportRepository;

    @PostMapping(path="/add")
    public ResponseEntity<Airport> addNewAirport (@Valid @RequestBody Airport airport) {
        airportRepository.save(airport);

        return new ResponseEntity<>(airport, HttpStatus.OK);
    }

    @GetMapping(path = "/findBy")
    public ResponseEntity<Object> findByTicketNumber(@RequestParam(value = "name", defaultValue = "") String name) {
        Airport airport = airportRepository.findByName(name);

        return new ResponseEntity<>(airport, null, HttpStatus.OK);
    }
}
