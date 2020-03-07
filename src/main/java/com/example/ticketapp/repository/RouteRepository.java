package com.example.ticketapp.repository;

import com.example.ticketapp.entity.Route;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteRepository extends CrudRepository<Route, Integer> {

    @Query("Select r from Route r where (:start is null or r.start = :start) and (:destination is null or r.destination = :destination)")
    List<Route> findRoute(@Param("start") String start, @Param("destination") String destination);

}