package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AddressRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;

import java.util.List;

/**
 * Service interface for address management.
 * Defines the contract for CRUD operations on addresses.
 * Implemented by AddressServiceImpl.
 */
public interface IAddressService {
    /** Creates a new address and returns the saved data */
    AddressResponse createAddress(AddressRequest request);

    /** Retrieves an address by its unique ID */
    AddressResponse getAddressById(Integer addressId);

    /** Retrieves all addresses from the database */
    List<AddressResponse> getAllAddresses();

    /** Updates an existing address with new data */
    AddressResponse updateAddress(Integer addressId, AddressRequest request);

    /** Deletes an address by its ID */
    void deleteAddress(Integer addressId);
}
