package com.example.ticketapp.controller;

import com.example.ticketapp.entity.Airport;
import com.example.ticketapp.entity.Route;
import com.example.ticketapp.entity.Ticket;
import com.example.ticketapp.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path="/route")
public class RouteController {
    @Autowired
    private RouteRepository routeRepository;

    @PostMapping(path="/add")
    public ResponseEntity<Route> addNewRoute (@Valid @RequestBody Route route) {
        routeRepository.save(route);

        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    @GetMapping(path = "/findBy")
    public ResponseEntity<Object> findRoute(@RequestParam(value = "start", required = false) String start,
                                                     @RequestParam(value = "destination", required = false) String destination) {

        List<Route> routes = routeRepository.findRoute(start, destination);

        return new ResponseEntity<>(routes, null, HttpStatus.OK);
    }
}
