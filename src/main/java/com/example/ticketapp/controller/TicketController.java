package com.example.ticketapp.controller;

import com.example.ticketapp.entity.Flight;
import com.example.ticketapp.entity.Ticket;
import com.example.ticketapp.entity.User;
import com.example.ticketapp.repository.FlightRepository;
import com.example.ticketapp.repository.TicketRepository;
import com.example.ticketapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping(path="/ticket")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightRepository flightRepository;

    @PostMapping(path="/add")
    public ResponseEntity<Object> addNewTicket (@Valid @RequestBody Ticket ticket) {
        User user = userRepository.findUserByEmail(ticket.getUser().getEmail());

        if (user != null) {
            ticket.setUser(user);
        } else {
            userRepository.save(ticket.getUser());
        }

        Optional<Flight> flight = flightRepository.findById(ticket.getFlight().getId());

        if(flight.isPresent()) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp flightStartDate = flight.get().getStartDate();

            if (now.compareTo(flightStartDate) >= 0) {
                return new ResponseEntity<>("flight start date must be greater than current date", HttpStatus.BAD_REQUEST);
            }

            Integer quota = flight.get().getQuota();
            Set<Ticket> tickets = ticketRepository.findAllByFlightAndIsCancelled(flight.get(), false);

            if (tickets.size() >= quota) {
                return new ResponseEntity<>("quota is full", HttpStatus.BAD_REQUEST);
            }

            BigDecimal price = flight.get().getPrice();

            if (tickets.size() >= quota * 0.3) {
                price = price.multiply(new BigDecimal("1.3"));
            } else if (tickets.size() >= quota * 0.2) {
                price = price.multiply(new BigDecimal("1.2"));
            } else if (tickets.size() >= quota * 0.1) {
                price = price.multiply(new BigDecimal("1.1"));
            }

            ticket.setPrice(price);

            ticketRepository.save(ticket);
        }

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping(path = "/cancel")
    public ResponseEntity<Object> cancelTicket(@RequestParam(value = "ticketNumber") String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);

        if (ticket == null) {
            return new ResponseEntity<>("ticket not found", HttpStatus.BAD_REQUEST);
        }

        Optional<Flight> flight = flightRepository.findById(ticket.getFlight().getId());

        if(flight.isPresent()) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp flightStartDate = flight.get().getStartDate();

            if (now.compareTo(flightStartDate) >= 0) {
                return new ResponseEntity<>("flight start date must be greater than current date", HttpStatus.BAD_REQUEST);
            }

            ticket.setCancelled(true);

            ticketRepository.save(ticket);
        } else {
            return new ResponseEntity<>("flight is empty", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping(path = "/findBy")
    public ResponseEntity<Object> findByTicketNumber(@RequestParam(value = "ticketNumber", defaultValue = "") String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);

        return new ResponseEntity<>(ticket, null, HttpStatus.OK);
    }
}

