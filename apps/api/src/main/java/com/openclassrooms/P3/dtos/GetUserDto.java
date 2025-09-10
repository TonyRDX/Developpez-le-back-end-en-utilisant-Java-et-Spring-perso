package com.openclassrooms.P3.dtos;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class GetUserDto {
    private Integer id;
    private String email;
    private String name;
    private Instant createdAt; 
    private Instant updatedAt;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                                .withZone(ZoneId.systemDefault());

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedAt() {
        return formatter.format(updatedAt);
    }
    public String getCreatedAt() {
        return formatter.format(createdAt);
    }
}
