package com.openclassrooms.P3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.P3.model.Rental;
import com.openclassrooms.P3.repository.RentalRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public Iterable<Rental> getAllEmployees() {
        return rentalRepository.findAll();
    }
}
