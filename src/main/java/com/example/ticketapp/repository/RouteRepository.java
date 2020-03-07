package com.example.ticketapp.repository;

import com.example.ticketapp.entity.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RouteRepository extends CrudRepository<Route, Integer> {

    List<Route> findAllByStartOrDestination(String start, String destination);

}