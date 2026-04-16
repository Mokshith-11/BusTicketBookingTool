package com.gemini.BusTicketBookingSystem.Service;


import com.gemini.BusTicketBookingSystem.Dto.Request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.CustomerResponse;

import java.util.List;

public interface ICustomerService {
    CustomerResponse registerCustomer(CustomerRequest requestDTO);
    CustomerResponse getCustomerById(Integer customerId);
    CustomerResponse updateCustomer(Integer customerId, CustomerRequest requestDTO);
    List<CustomerResponse> getAllCustomers();
}




