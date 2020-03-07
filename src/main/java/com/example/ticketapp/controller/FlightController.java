package com.example.ticketapp.controller;

import com.example.ticketapp.entity.Airport;
import com.example.ticketapp.entity.Company;
import com.example.ticketapp.entity.Flight;
import com.example.ticketapp.entity.Route;
import com.example.ticketapp.repository.AirportRepository;
import com.example.ticketapp.repository.CompanyRepository;
import com.example.ticketapp.repository.FlightRepository;
import com.example.ticketapp.repository.RouteRepository;
import com.example.ticketapp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/flight")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private RouteRepository routeRepository;

    @PostMapping(path="/add")
    public ResponseEntity<Object> addNewFlight (@Valid @RequestBody Flight flight) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Optional<Company> company = companyRepository.findById(flight.getCompany().getId());
        if (!company.isPresent()) {
            return new ResponseEntity<>(Utils.messageToMap("company not found"), HttpStatus.BAD_REQUEST);
        }

        Optional<Airport> airport = airportRepository.findById(flight.getAirport().getId());
        if (!airport.isPresent()) {
            return new ResponseEntity<>(Utils.messageToMap("airport not found"), HttpStatus.BAD_REQUEST);
        }

        Optional<Route> route = routeRepository.findById(flight.getRoute().getId());
        if (!route.isPresent()) {
            return new ResponseEntity<>(Utils.messageToMap("route not found"), HttpStatus.BAD_REQUEST);
        }

        if (now.compareTo(flight.getStartDate()) >= 0) {
            return new ResponseEntity<>(Utils.messageToMap("start date must be greater than current date"), HttpStatus.BAD_REQUEST);
        }

        if (flight.getStartDate().compareTo(flight.getEndDate()) >= 0) {
            return new ResponseEntity<>(Utils.messageToMap("end date must be greater than start date"), HttpStatus.BAD_REQUEST);
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