package com.openclassrooms.P3.dtos;

import org.springframework.web.multipart.MultipartFile;

public class CreateRentalDto {
    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;
    
    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Double getSurface() {
        return surface;
    }

    public Double getPrice() {
        return price;
    }

    public void setPicture(MultipartFile file) {
        this.picture = file;
    };


    public String getPicture() {
        return picture.getOriginalFilename();
    }

    public String getDescription() {
        return description;
    }
}
