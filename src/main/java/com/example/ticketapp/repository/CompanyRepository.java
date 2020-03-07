package com.example.ticketapp.repository;

import com.example.ticketapp.entity.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

    Company findByName (String name);

}
