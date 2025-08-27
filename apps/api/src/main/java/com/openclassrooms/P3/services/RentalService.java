package com.openclassrooms.P3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.openclassrooms.P3.model.Rental;
import com.openclassrooms.P3.repository.RentalRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    
    public List<Rental> getAllRentals(int page, int size, String sortDir, String sort) {
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        
        Page<Rental> posts = rentalRepository.findAll(pageReq);
        return posts.getContent();
    }
}
