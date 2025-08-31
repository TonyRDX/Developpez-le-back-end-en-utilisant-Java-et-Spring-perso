package com.openclassrooms.P3.services;

import java.util.List;
import java.util.NoSuchElementException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.openclassrooms.P3.dtos.UpdateRentalDto;
import com.openclassrooms.P3.model.Rental;
import com.openclassrooms.P3.repository.RentalRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    public List<Rental> getAllRentals(int page, int size, String sortDir, String sort) {
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        
        Page<Rental> posts = rentalRepository.findAll(pageReq);
        return posts.getContent();
    }

    public Rental updateRental(UpdateRentalDto updateRentalDto, int userId) throws NoSuchElementException {
        Rental persistedRental = rentalRepository.findById(updateRentalDto.getId()).orElseThrow();
        if ((int) persistedRental.getOwnerId() != userId) {
            throw new AccessDeniedException(null);
        }
        modelMapper.map(updateRentalDto, persistedRental);

        rentalRepository.save(persistedRental);
        return persistedRental;
    }
}
