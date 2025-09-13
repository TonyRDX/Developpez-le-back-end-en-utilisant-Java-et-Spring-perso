package com.openclassrooms.P3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.P3.beans.UserContext;
import com.openclassrooms.P3.dtos.CreateMessageDto;
import com.openclassrooms.P3.model.Message;
import com.openclassrooms.P3.model.Rental;
import com.openclassrooms.P3.model.User;
import com.openclassrooms.P3.repository.MessageRepository;
import com.openclassrooms.P3.repository.RentalRepository;
import com.openclassrooms.P3.repository.UserRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserContext userContext;

    public Message create(CreateMessageDto createMessageDto) {        
        Message newMessage = new Message();
        newMessage.setMessage(createMessageDto.getMessage());

        User user = userRepository.getReferenceById(this.userContext.getUserId());
        newMessage.setUser(user);

        Rental rental = rentalRepository.getReferenceById(createMessageDto.getRentalId());
        newMessage.setRental(rental);

        return messageRepository.save(newMessage);
    }
}
