package com.openclassrooms.P3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.P3.model.Rental;
import com.openclassrooms.P3.services.RentalService;

@RestController
public class RentalContoller {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/api/rentals")
    public Iterable<Rental> getAllRentals() {
        return this.rentalService.getAllEmployees();
    }
}