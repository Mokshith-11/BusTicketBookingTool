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

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;


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