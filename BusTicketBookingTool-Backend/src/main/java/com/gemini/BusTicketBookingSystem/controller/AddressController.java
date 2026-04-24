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
/*
 * - This controller is the API entry point for Address requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class AddressController {

    @Autowired
    private IAddressService addressService;
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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
