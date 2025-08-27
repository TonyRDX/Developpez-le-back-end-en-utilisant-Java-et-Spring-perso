package com.openclassrooms.P3.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.P3.model.Rental;

@Repository
public interface RentalRepository extends PagingAndSortingRepository<Rental, Integer> {
}