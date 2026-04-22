package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.dto.response.CustomerResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.ICustomerService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing customers.
 * Handles registering new customers, viewing customer details,
 * updating customer information, and listing all customers.
 * Base URL: /api/v1/customers
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;


    /**
     * Registers a new customer in the system.
     * Validates that the email and phone are unique (not already registered).
     * Links the customer to an existing address using the addressId in the request.
     *
     * @param requestDTO - customer details: name, email, phone, addressId (validated with @Valid)
     * @return ResponseEntity with HTTP 201 (Created) status and the registered customer data
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> registerCustomer(
            @Valid @RequestBody CustomerRequest requestDTO) {

        CustomerResponse response = customerService.registerCustomer(requestDTO);

        ApiResponse<CustomerResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Customer registered successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    /**
     * Retrieves a single customer by their unique customer ID.
     * If the customer is not found, a ResourceNotFoundException is thrown.
     *
     * @param customerId - the unique identifier of the customer to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and the customer details
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(
            @PathVariable Integer customerId) {

        CustomerResponse response = customerService.getCustomerById(customerId);

        ApiResponse<CustomerResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Customer fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Updates an existing customer's details.
     * Finds the customer by ID and updates their name, email, phone, and address.
     * Checks for duplicate email/phone if those values are being changed.
     *
     * @param customerId - the unique ID of the customer to update
     * @param requestDTO - the new customer details (validated with @Valid)
     * @return ResponseEntity with HTTP 200 (OK) and the updated customer data
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Integer customerId,
            @Valid @RequestBody CustomerRequest requestDTO) {

        CustomerResponse response = customerService.updateCustomer(customerId, requestDTO);

        ApiResponse<CustomerResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Customer updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Retrieves a list of all registered customers.
     * Returns every customer stored in the database.
     *
     * @return ResponseEntity with HTTP 200 (OK) and a list of all customers
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {

        List<CustomerResponse> customers = customerService.getAllCustomers();

        ApiResponse<List<CustomerResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Customers fetched successfully",
                        customers);

        return ResponseEntity.ok(apiResponse);
    }
}