package com.gemini.BusTicketBookingSystem.controller;


import com.gemini.BusTicketBookingSystem.dto.request.AddressRequest;

import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST Controller for managing addresses.
 * Handles HTTP requests related to creating and retrieving addresses.
 * Base URL: /api/v1/addresses
 */
@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    /**
     * Creates a new address in the system.
     * Accepts address details (street, city, state, zip) in the request body,
     * saves them to the database, and returns the newly created address.
     *
     * @param request - the address details to be saved (address, city, state, zipCode)
     * @return ResponseEntity with HTTP 201 (Created) status and the saved address data
     */
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
            @RequestBody AddressRequest request) {

        AddressResponse response = addressService.createAddress(request);

        ApiResponse<AddressResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Address created successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Retrieves a specific address by its unique ID.
     * Looks up the address in the database using the provided ID.
     * If the address is not found, a ResourceNotFoundException is thrown.
     *
     * @param id - the unique identifier of the address to retrieve
     * @return ResponseEntity with HTTP 200 (OK) status and the address data
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddress(@PathVariable Integer id) {

        AddressResponse response = addressService.getAddressById(id);

        ApiResponse<AddressResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Address fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }



}
