package com.gemini.BusTicketBookingSystem.Service.Impl;

import com.gemini.BusTicketBookingSystem.Dto.Request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.CustomerResponse;
import com.gemini.BusTicketBookingSystem.Entity.Addresses;
import com.gemini.BusTicketBookingSystem.Entity.Customer;
import com.gemini.BusTicketBookingSystem.Exception.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.Exception.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.Repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.Repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.Service.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
 class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IAddressesRepository addressRepository;

    @Override
    @Transactional
    public CustomerResponse registerCustomer(CustomerRequest requestDTO) {
        // Check for duplicate email
        if (customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Customer", "email", requestDTO.getEmail());
        }

        // Check for duplicate phone
        if (customerRepository.existsByPhone(requestDTO.getPhone())) {
            throw new DuplicateResourceException("Customer", "phone", requestDTO.getPhone());
        }

        Addresses address = addressRepository.findById(requestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId",
                        requestDTO.getAddressId()));

        Customer customer = new Customer();
        customer.setName(requestDTO.getName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPhone(requestDTO.getPhone());
        customer.setAddress(address);

        Customer savedCustomer = customerRepository.save(customer);
        return convertToResponseDTO(savedCustomer);
    }


    public CustomerResponse getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
        return convertToResponseDTO(customer);
    }


    @Transactional
    public CustomerResponse updateCustomer(Integer customerId, CustomerRequest requestDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));

        // Check if email is being changed and if it's duplicate
        if (!customer.getEmail().equals(requestDTO.getEmail()) &&
                customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Customer", "email", requestDTO.getEmail());
        }

        // Check if phone is being changed and if it's duplicate
        if (!customer.getPhone().equals(requestDTO.getPhone()) &&
                customerRepository.existsByPhone(requestDTO.getPhone())) {
            throw new DuplicateResourceException("Customer", "phone", requestDTO.getPhone());
        }

        Addresses address = addressRepository.findById(requestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId",
                        requestDTO.getAddressId()));

        customer.setName(requestDTO.getName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPhone(requestDTO.getPhone());
        customer.setAddress(address);

        Customer updatedCustomer = customerRepository.save(customer);
        return convertToResponseDTO(updatedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private CustomerResponse convertToResponseDTO(Customer customer) {
        CustomerResponse dto = new CustomerResponse();
        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
//        dto.setAddressId(customer.getAddress().getAddressId());
//        dto.setAddress(customer.getAddress().getAddress() + ", " +
//                customer.getAddress().getCity() + ", " +
//                customer.getAddress().getState());
        return dto;
    }
}