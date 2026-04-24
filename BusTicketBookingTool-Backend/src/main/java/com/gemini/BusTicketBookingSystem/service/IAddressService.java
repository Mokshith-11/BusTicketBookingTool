package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AddressRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;

import java.util.List;
/*
 * - This service interface lists the Address actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

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
