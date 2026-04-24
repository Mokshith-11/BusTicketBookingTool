package com.gemini.BusTicketBookingSystem.serviceTesting;

import com.gemini.BusTicketBookingSystem.dto.request.CustomerRequest;
import com.gemini.BusTicketBookingSystem.dto.response.CustomerResponse;
import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.entity.Customer;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;


import com.gemini.BusTicketBookingSystem.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private IAddressesRepository addressRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private Addresses address;
    private CustomerRequest request;

    @BeforeEach
    void setUp() {
        address = new Addresses();
        address.setAddressId(1);

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("John");
        customer.setEmail("john@gmail.com");
        customer.setPhone("9876543210");
        customer.setAddress(address);

        request = new CustomerRequest();
        request.setName("John");
        request.setEmail("john@gmail.com");
        request.setPhone("9876543210");
        request.setAddressId(1);
    }

    // REGISTER SUCCESS
    @Test
    void registerCustomer_success() {
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhone(request.getPhone())).thenReturn(false);
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponse response = customerService.registerCustomer(request);

        assertNotNull(response);
        assertEquals("John", response.getName());
        verify(customerRepository).save(any(Customer.class));
    }

    // ❌ DUPLICATE EMAIL
    @Test
    void registerCustomer_duplicateEmail() {
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> customerService.registerCustomer(request));

        verify(customerRepository, never()).save(any());
    }

    // ❌ DUPLICATE PHONE
    @Test
    void registerCustomer_duplicatePhone() {
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhone(request.getPhone())).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> customerService.registerCustomer(request));
    }

    // ❌ ADDRESS NOT FOUND
    @Test
    void registerCustomer_addressNotFound() {
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhone(request.getPhone())).thenReturn(false);
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.registerCustomer(request));
    }

    // GET BY ID SUCCESS
    @Test
    void getCustomerById_success() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getCustomerById(1);

        assertEquals("John", response.getName());
        verify(customerRepository).findById(1);
    }

    // ❌ GET BY ID NOT FOUND
    @Test
    void getCustomerById_notFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.getCustomerById(1));
    }

    // UPDATE SUCCESS (no duplicate check triggered)
    @Test
    void updateCustomer_success() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponse response = customerService.updateCustomer(1, request);

        assertEquals("John", response.getName());
        verify(customerRepository).save(any(Customer.class));
    }

    // ❌ UPDATE NOT FOUND
    @Test
    void updateCustomer_notFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.updateCustomer(1, request));
    }

    // ❌ UPDATE DUPLICATE EMAIL (only when changed)
    @Test
    void updateCustomer_duplicateEmail() {
        Customer existing = new Customer();
        existing.setCustomerId(1);
        existing.setEmail("old@gmail.com");
        existing.setPhone("9876543210");

        when(customerRepository.findById(1)).thenReturn(Optional.of(existing));
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> customerService.updateCustomer(1, request));
    }

    // ❌ UPDATE DUPLICATE PHONE (only when changed)
    @Test
    void updateCustomer_duplicatePhone() {
        Customer existing = new Customer();
        existing.setCustomerId(1);
        existing.setEmail("john@gmail.com");
        existing.setPhone("1111111111");

        when(customerRepository.findById(1)).thenReturn(Optional.of(existing));
        when(customerRepository.existsByPhone(request.getPhone())).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> customerService.updateCustomer(1, request));
    }

    // GET ALL
    @Test
    void getAllCustomers_success() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<CustomerResponse> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        verify(customerRepository).findAll();
    }
}