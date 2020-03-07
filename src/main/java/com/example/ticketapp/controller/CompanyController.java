package com.example.ticketapp.controller;

import com.example.ticketapp.entity.Airport;
import com.example.ticketapp.entity.Company;
import com.example.ticketapp.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/company")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping(path="/add")
    public ResponseEntity<Company> addNewCompany (@Valid @RequestBody Company company) {
        companyRepository.save(company);

        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping(path = "/findBy")
    public ResponseEntity<Object> findByName(@RequestParam(value = "name", defaultValue = "") String name) {
        Company company = companyRepository.findByName(name);

        return new ResponseEntity<>(company, null, HttpStatus.OK);
    }
}

