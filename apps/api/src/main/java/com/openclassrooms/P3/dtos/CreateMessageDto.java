package com.openclassrooms.P3.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateMessageDto {
    private String message;
    @JsonProperty("user_id") private Integer userId; // TODO Unused. check to remove that security issue
    @JsonProperty("rental_id") private Integer rentalId;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getRentalId() {
        return rentalId;
    }
    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    
}
