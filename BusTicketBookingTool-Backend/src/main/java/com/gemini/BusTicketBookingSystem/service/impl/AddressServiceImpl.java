package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.service.IAddressService;
import com.gemini.BusTicketBookingSystem.dto.request.AddressRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
/*
 * Beginner guide:
 * - This class contains the real business logic for Address operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */

public class AddressServiceImpl implements IAddressService {
@Autowired
    private IAddressesRepository addressRepository;

    @Override
    public AddressResponse createAddress(AddressRequest request) {
        Addresses address = Addresses.builder()
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .build();
        return mapToResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse getAddressById(Integer addressId) {
        Addresses address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
        return mapToResponse(address);
    }

    @Override
    public List<AddressResponse> getAllAddresses() {
        return addressRepository.findAll()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public AddressResponse updateAddress(Integer addressId, AddressRequest request) {
        Addresses address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        return mapToResponse(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(Integer addressId) {
        Addresses address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
        addressRepository.delete(address);
    }

    private AddressResponse mapToResponse(Addresses address) {
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .address(address.getAddress())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }
}
