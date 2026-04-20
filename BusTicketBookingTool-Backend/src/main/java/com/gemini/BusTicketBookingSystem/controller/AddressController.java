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


@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    @Autowired
    private IAddressService addressService;

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
