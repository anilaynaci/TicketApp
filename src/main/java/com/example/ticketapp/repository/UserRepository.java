package com.example.ticketapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ticketapp.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserByEmail(String email);

}