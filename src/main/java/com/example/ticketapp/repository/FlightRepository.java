package com.example.ticketapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.ticketapp.entity.Flight;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Integer> {

    @Query("SELECT f " +
            "FROM Flight f " +
            "WHERE " +
            "(:airportName is null or f.airport.name = :airportName) and " +
            "(:companyName is null or f.company.name = :companyName) and " +
            "(:routeStart is null or f.route.start = :routeStart) and " +
            "(:routeDestination is null or f.route.destination = :routeDestination) " +
            "order by f.startDate")
    List<Flight> findFlight(
            @Param("airportName") String airportName,
            @Param("companyName") String companyName,
            @Param("routeStart") String routeStart,
            @Param("routeDestination") String routeDestination);

}