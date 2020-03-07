package com.example.ticketapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ticketapp.entity.Flight;

public interface FlightRepository extends CrudRepository<Flight, Integer> {

}