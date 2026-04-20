package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.dto.response.CustomerResponse;

import java.util.List;

public interface ICustomerService {
    CustomerResponse registerCustomer(CustomerRequest requestDTO);
    CustomerResponse getCustomerById(Integer customerId);
    CustomerResponse updateCustomer(Integer customerId, CustomerRequest requestDTO);
    List<CustomerResponse> getAllCustomers();
}




