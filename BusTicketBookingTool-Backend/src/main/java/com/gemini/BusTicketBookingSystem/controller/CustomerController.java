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
/*
 * Beginner guide:
 * - This controller is the API entry point for Customer requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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
    /*
     * PUT flow:
     * - URL gives the record ID and the body gives the new values.
     * - @Valid checks the body, then service finds the old record and updates it.
     * - If the ID does not exist, the service throws ResourceNotFoundException.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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