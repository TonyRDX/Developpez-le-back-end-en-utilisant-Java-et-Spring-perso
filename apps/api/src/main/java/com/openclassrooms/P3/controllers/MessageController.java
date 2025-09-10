package com.openclassrooms.P3.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import com.openclassrooms.P3.dtos.CreateMessageDto;
import com.openclassrooms.P3.services.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Operation(summary = "Create a new message", description = "")
    @PostMapping
    public Map<String, String> create(@RequestBody CreateMessageDto createMessageDto) {
        this.messageService.create(createMessageDto);
        return Map.of("message","Message send with success");
    }
}