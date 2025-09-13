package com.openclassrooms.P3.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.openclassrooms.P3.dtos.CreateRentalDto;
import com.openclassrooms.P3.dtos.RentalDto;
import com.openclassrooms.P3.dtos.UpdateRentalDto;
import com.openclassrooms.P3.model.Rental;
import com.openclassrooms.P3.services.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalContoller {
    @Autowired
    private RentalService rentalService;

    @Autowired
    private ModelMapper modelMapper;

    private RentalDto convertToDto(Rental rental) {
        return modelMapper.map(rental, RentalDto.class);
    }

    private Rental convertToEntity(RentalDto rentalDto) {
        return modelMapper.map(rentalDto, Rental.class);
    }

    @ApiResponse(
        responseCode = "200", 
        description = "Get all rentals", 
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Response example",
                value = """
                   { 
                        "rentals": [
                            {
                                "id": 1,
                                "name": "test house 1",
                                "surface": 432,
                                "price": 300,
                                "picture": "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg",
                                "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam a lectus eleifend, varius massa ac, mollis tortor. Quisque ipsum nulla, faucibus ac metus a, eleifend efficitur augue. Integer vel pulvinar ipsum. Praesent mollis neque sed sagittis ultricies. Suspendisse congue ligula at justo molestie, eget cursus nulla tincidunt. Pellentesque elementum rhoncus arcu, viverra gravida turpis mattis in. Maecenas tempor elementum lorem vel ultricies. Nam tempus laoreet eros, et viverra libero tincidunt a. Nunc vel nisi vulputate, sodales massa eu, varius erat.",
                                "owner_id": 1,
                                "created_at": "2012/12/02",
                                "updated_at": "2014/12/02"  
                            },
                            {
                                "id": 1,
                                "name": "test house 2",
                                "surface": 154,
                                "price": 200,
                                "picture": "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg",
                                "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam a lectus eleifend, varius massa ac, mollis tortor. Quisque ipsum nulla, faucibus ac metus a, eleifend efficitur augue. Integer vel pulvinar ipsum. Praesent mollis neque sed sagittis ultricies. Suspendisse congue ligula at justo molestie, eget cursus nulla tincidunt. Pellentesque elementum rhoncus arcu, viverra gravida turpis mattis in. Maecenas tempor elementum lorem vel ultricies. Nam tempus laoreet eros, et viverra libero tincidunt a. Nunc vel nisi vulputate, sodales massa eu, varius erat.",
                                "owner_id": 2,
                                "created_at": "2012/12/02",
                                "updated_at": "2014/12/02"  
                            }
                        ]
                    }
                """
            )
        )
    )
    @Operation(summary = "Rentals list", description = "Returns all rental")
    @GetMapping
    public Map<String, List<RentalDto>> getAllRentals() {
        // TODO check if paging is useful. Now it is used as example
        return Map.of(
            "rentals",
            this.rentalService.getAllRentals(0, 100, "asc", "id")
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putRental(@PathVariable Integer id, @ModelAttribute UpdateRentalDto updateRentalDto) {
        try {
            Rental rentalToUpdate = this.rentalService.updateRental(updateRentalDto);
            RentalDto updated = this.convertToDto(rentalToUpdate);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found (wrong id).");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can edit only Rental you own.");
        }
    }

    // TODO explain imperative vs declarative, meta programming approach, or cross cutting concerns
    @Operation(summary = "Get one rental", description = "Returns the rental matching by id")
    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalDto> getOneRental(@PathVariable Integer rentalId) {
        Rental rental = this.rentalService.getById(rentalId);
        return ResponseEntity.ok(modelMapper.map(rental, RentalDto.class));
    }

    @Operation(summary = "Create a new rental", description = "Returns the rental new rental once created")
    @PostMapping
    public ResponseEntity<RentalDto> create(@ModelAttribute CreateRentalDto createRentalDto) throws IOException {
        Rental rental = this.rentalService.create(createRentalDto);
        return ResponseEntity.ok(modelMapper.map(rental, RentalDto.class));
    }
} 
