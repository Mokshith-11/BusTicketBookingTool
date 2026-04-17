package com.gemini.BusTicketBookingSystem.controller;


import com.gemini.BusTicketBookingSystem.dto.request.AddressRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;
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
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest requestDTO) {
        AddressResponse response = addressService.createAddress(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Integer addressId) {
        AddressResponse response = addressService.getAddressById(addressId);
        return ResponseEntity.ok(response);
    }
}







