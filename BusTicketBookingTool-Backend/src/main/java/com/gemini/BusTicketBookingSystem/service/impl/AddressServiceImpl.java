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

/**
 * Service implementation for managing addresses.
 * Contains the business logic for creating, retrieving, updating,
 * and deleting address records in the database.
 */
@Service
/*
 * - This class contains the real business logic for Address operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */

public class AddressServiceImpl implements IAddressService {
@Autowired
    private IAddressesRepository addressRepository;

    /**
     * Creates a new address record in the database.
     * Builds an Address entity from the request data and saves it.
     *
     * @param request - contains address, city, state, and zipCode
     * @return AddressResponse - the saved address data with generated ID
     */
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

    /**
     * Retrieves a single address by its ID.
     * Throws ResourceNotFoundException if no address exists with the given ID.
     *
     * @param addressId - the unique ID of the address to find
     * @return AddressResponse - the address data
     */
    @Override
    public AddressResponse getAddressById(Integer addressId) {
        Addresses address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
        return mapToResponse(address);
    }

    /**
     * Retrieves all addresses stored in the database.
     * Maps each Address entity to an AddressResponse DTO.
     *
     * @return List of AddressResponse - all addresses
     */
    @Override
    public List<AddressResponse> getAllAddresses() {
        return addressRepository.findAll()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Updates an existing address with new data.
     * Finds the address by ID, updates all fields, and saves it back.
     * Throws ResourceNotFoundException if the address doesn't exist.
     *
     * @param addressId - the ID of the address to update
     * @param request   - the new address data
     * @return AddressResponse - the updated address data
     */
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

    /**
     * Deletes an address from the database by its ID.
     * Throws ResourceNotFoundException if the address doesn't exist.
     *
     * @param addressId - the ID of the address to delete
     */
    @Override
    public void deleteAddress(Integer addressId) {
        Addresses address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
        addressRepository.delete(address);
    }

    /**
     * Helper method to convert an Address entity to an AddressResponse DTO.
     * Maps all fields from the entity to the response object.
     *
     * @param address - the Address entity to convert
     * @return AddressResponse - the mapped DTO
     */
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
