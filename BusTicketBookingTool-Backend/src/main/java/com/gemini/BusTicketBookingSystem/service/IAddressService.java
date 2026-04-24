package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AddressRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;

import java.util.List;
/*
 * - This service interface lists the Address actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface IAddressService {
    AddressResponse createAddress(AddressRequest request);
    AddressResponse getAddressById(Integer addressId);
    List<AddressResponse> getAllAddresses();
    AddressResponse updateAddress(Integer addressId, AddressRequest request);
    void deleteAddress(Integer addressId);
}
