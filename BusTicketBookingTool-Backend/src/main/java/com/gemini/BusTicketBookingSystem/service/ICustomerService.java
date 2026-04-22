package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.dto.response.CustomerResponse;

import java.util.List;

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
