package com.gemini.BusTicketBookingSystem.serviceTesting;



import com.gemini.BusTicketBookingSystem.dto.request.AddressRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;
import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.service.impl.AddressServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private IAddressesRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Addresses address;
    private AddressRequest request;

    @BeforeEach
    void setUp() {

        address = Addresses.builder()
                .addressId(1)
                .address("Street 1")
                .city("Chennai")
                .state("TN")
                .zipCode("600001")
                .build();

        request = new AddressRequest();
        request.setAddress("Street 1");
        request.setCity("Chennai");
        request.setState("TN");
        request.setZipCode("600001");
    }

    // ✅ CREATE ADDRESS
    @Test
    void testCreateAddress() {

        when(addressRepository.save(any(Addresses.class))).thenReturn(address);

        AddressResponse response = addressService.createAddress(request);

        assertNotNull(response);
        assertEquals("Street 1", response.getAddress());

        verify(addressRepository, times(1)).save(any(Addresses.class));
    }

    // ✅ GET BY ID - SUCCESS
    @Test
    void testGetAddressById_Success() {

        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        AddressResponse response = addressService.getAddressById(1);

        assertEquals("Chennai", response.getCity());

        verify(addressRepository, times(1)).findById(1);
    }

    // ❌ GET BY ID - NOT FOUND
    @Test
    void testGetAddressById_NotFound() {

        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                addressService.getAddressById(1)
        );

        verify(addressRepository, times(1)).findById(1);
    }

    // ✅ UPDATE ADDRESS - SUCCESS
    @Test
    void testUpdateAddress_Success() {

        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Addresses.class))).thenReturn(address);

        AddressResponse response = addressService.updateAddress(1, request);

        assertEquals("Street 1", response.getAddress());

        verify(addressRepository).findById(1);
        verify(addressRepository).save(any(Addresses.class));
    }

    // ❌ UPDATE ADDRESS - NOT FOUND
    @Test
    void testUpdateAddress_NotFound() {

        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                addressService.updateAddress(1, request)
        );

        verify(addressRepository).findById(1);
        verify(addressRepository, never()).save(any());
    }

    // ✅ DELETE ADDRESS - SUCCESS
    @Test
    void testDeleteAddress_Success() {

        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        addressService.deleteAddress(1);

        verify(addressRepository).findById(1);
        verify(addressRepository).delete(address);
    }

    // ❌ DELETE ADDRESS - NOT FOUND
    @Test
    void testDeleteAddress_NotFound() {

        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                addressService.deleteAddress(1)
        );

        verify(addressRepository).findById(1);
        verify(addressRepository, never()).delete(any());
    }
}
