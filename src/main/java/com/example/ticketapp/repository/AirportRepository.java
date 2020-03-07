package com.example.ticketapp.repository;

import com.example.ticketapp.entity.Airport;
import org.springframework.data.repository.CrudRepository;

public interface AirportRepository extends CrudRepository<Airport, Integer> {

    Airport findByName (String name);

}
