package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.dto.response.CustomerResponse;

import java.util.List;
/*
 * - This service interface lists the Customer actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

/**
 * Service interface for customer management.
 * Defines the contract for registering, viewing, and updating customers.
 * Implemented by CustomerServiceImpl.
 */
public interface ICustomerService {
    /** Registers a new customer with unique email and phone */
    CustomerResponse registerCustomer(CustomerRequest requestDTO);

    /** Retrieves a customer by their unique ID */
    CustomerResponse getCustomerById(Integer customerId);

    /** Updates an existing customer's details */
    CustomerResponse updateCustomer(Integer customerId, CustomerRequest requestDTO);

    /** Retrieves all customers from the database */
    List<CustomerResponse> getAllCustomers();
}
