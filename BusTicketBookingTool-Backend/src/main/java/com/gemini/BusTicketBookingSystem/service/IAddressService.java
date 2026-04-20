package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AddressRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;

import java.util.List;

public interface IAddressService {
    AddressResponse createAddress(AddressRequest request);
    AddressResponse getAddressById(Integer addressId);
    List<AddressResponse> getAllAddresses();
    AddressResponse updateAddress(Integer addressId, AddressRequest request);
    void deleteAddress(Integer addressId);
}
