package com.example.ticketapp.repository;

import com.example.ticketapp.entity.Flight;
import org.springframework.data.repository.CrudRepository;

import com.example.ticketapp.entity.Ticket;

import java.util.Set;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    Set<Ticket> findAllByFlightIdAndIsCancelled (Integer flight, Boolean cancelled);
    Ticket findByTicketNumber (String ticketNumber);

}