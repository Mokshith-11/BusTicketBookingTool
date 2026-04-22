package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.dto.request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.dto.response.CustomerResponse;
import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.entity.Customer;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.service.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing customers.
 * Contains business logic for registering, retrieving, and updating
 * customer records. Ensures email and phone uniqueness.
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IAddressesRepository addressRepository;

    /**
     * Registers a new customer in the system.
     * Validates that both the email and phone number are unique
     * (not already used by another customer). Links the customer
     * to an existing address using the provided addressId.
     * This method is transactional to ensure data consistency.
     *
     * @param requestDTO - contains name, email, phone, addressId
     * @return CustomerResponse - the registered customer data with generated ID
     */
    @Override
    @Transactional
    public CustomerResponse registerCustomer(CustomerRequest requestDTO) {
        if (customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Customer", "email", requestDTO.getEmail());
        }

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


    /**
     * Retrieves a single customer by their unique ID.
     * Throws ResourceNotFoundException if no customer exists with that ID.
     *
     * @param customerId - the unique ID of the customer to find
     * @return CustomerResponse - the customer details
     */
    public CustomerResponse getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
        return convertToResponseDTO(customer);
    }


    /**
     * Updates an existing customer's details.
     * Finds the customer by ID, then checks for duplicate email/phone
     * only if those values are being changed (allows keeping the same email/phone).
     * Updates name, email, phone, and address.
     * This method is transactional to ensure data consistency.
     *
     * @param customerId - the ID of the customer to update
     * @param requestDTO - the new customer data
     * @return CustomerResponse - the updated customer data
     */
    @Transactional
    public CustomerResponse updateCustomer(Integer customerId, CustomerRequest requestDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));

        if (!customer.getEmail().equals(requestDTO.getEmail()) &&
                customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Customer", "email", requestDTO.getEmail());
        }

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

    /**
     * Retrieves all customers stored in the database.
     * Maps each Customer entity to a CustomerResponse DTO.
     *
     * @return List of CustomerResponse - all customers
     */
    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to convert a Customer entity to a CustomerResponse DTO.
     * Maps customer ID, name, email, and phone.
     *
     * @param customer - the Customer entity to convert
     * @return CustomerResponse - the mapped DTO
     */
    private CustomerResponse convertToResponseDTO(Customer customer) {
        CustomerResponse dto = new CustomerResponse();
        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }
}