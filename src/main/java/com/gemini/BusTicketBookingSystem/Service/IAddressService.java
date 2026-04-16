package com.gemini.BusTicketBookingSystem.Service;

import com.gemini.BusTicketBookingSystem.Dto.Request.AddressRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.AddressResponse;

import java.util.List;

public interface IAddressService {
    AddressResponse createAddress(AddressRequest request);
    AddressResponse getAddressById(Integer addressId);
    List<AddressResponse> getAllAddresses();
    AddressResponse updateAddress(Integer addressId, AddressRequest request);
    void deleteAddress(Integer addressId);
}
