package com.openclassrooms.P3.services;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.openclassrooms.P3.beans.UserContext;
import com.openclassrooms.P3.dtos.CreateRentalDto;
import com.openclassrooms.P3.dtos.UpdateRentalDto;
import com.openclassrooms.P3.model.Rental;
import com.openclassrooms.P3.model.User;
import com.openclassrooms.P3.repository.RentalRepository;
import com.openclassrooms.P3.repository.UserRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserContext userContext;

    @Autowired
    private FileService fs;
    
    public List<Rental> getAllRentals(int page, int size, String sortDir, String sort) {
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        
        Page<Rental> posts = rentalRepository.findAll(pageReq);
        return posts.getContent();
    }

    public Rental updateRental(UpdateRentalDto updateRentalDto) throws NoSuchElementException {
        Rental persistedRental = rentalRepository.findById(updateRentalDto.getId()).orElseThrow();
        if ((int) persistedRental.getOwner().getId() != this.userContext.getUserId()) {
            throw new AccessDeniedException(null);
        }
        modelMapper.map(updateRentalDto, persistedRental);

        rentalRepository.save(persistedRental);
        return persistedRental;
    }

    @Transactional(readOnly = true)
    public Rental getById(Integer id) {
        return rentalRepository.getById(id);
    }

    public Rental create(CreateRentalDto createRentalDto) throws IOException {
        Rental newRental = modelMapper.map(createRentalDto, Rental.class);

        User user = userRepository.getReferenceById(this.userContext.getUserId());
        newRental.setOwner(user);

        newRental.setPicture(fs.save(createRentalDto.getPictureFile()));

        rentalRepository.saveAndFlush(newRental);
        return newRental;
    }
}
