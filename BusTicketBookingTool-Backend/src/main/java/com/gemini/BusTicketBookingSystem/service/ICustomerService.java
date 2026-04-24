package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.dto.response.CustomerResponse;

import java.util.List;
/*
 * Beginner guide:
 * - This service interface lists the Customer actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface ICustomerService {
    CustomerResponse registerCustomer(CustomerRequest requestDTO);
    CustomerResponse getCustomerById(Integer customerId);
    CustomerResponse updateCustomer(Integer customerId, CustomerRequest requestDTO);
    List<CustomerResponse> getAllCustomers();
}




