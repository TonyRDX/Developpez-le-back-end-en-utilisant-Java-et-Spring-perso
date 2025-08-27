package com.openclassrooms.P3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.P3.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByName(String name);
}