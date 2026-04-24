package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.entity.Bus;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.repository.IBusRepository;
import com.gemini.BusTicketBookingSystem.service.IBusService;
import com.gemini.BusTicketBookingSystem.dto.request.BusRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing buses.
 * Contains business logic for registering, retrieving, updating,
 * and retiring buses that belong to agency offices.
 */
@Service
/*
 * - This class contains the real business logic for Bus operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */
public class BusServiceImpl implements IBusService {


    private final IBusRepository iBusRepository;

    /**
     * Constructor injection for the bus repository.
     *
     * @param iBusRepository - the repository for bus database operations
     */
    public BusServiceImpl(IBusRepository iBusRepository) {
        this.iBusRepository = iBusRepository;
    }

    @Autowired
    private IBusRepository busRepository;

    @Autowired
    private IAgencyOfficeRepository officeRepository;



    /**
     * Registers a new bus under a specific agency office.
     * First verifies the office exists, then checks that the registration number
     * is not already in use by another bus. Creates and saves the new bus.
     * This method is transactional to ensure data consistency.
     *
     * @param officeId   - the ID of the office this bus belongs to
     * @param requestDTO - contains registrationNumber, capacity, type
     * @return BusResponse - the registered bus data with generated ID
     */
    @Override
    @Transactional
    public BusResponse registerBus(Integer officeId, BusRequest requestDTO) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("AgencyOffice", "officeId", officeId));

        if (iBusRepository.existsByRegistrationNumber(requestDTO.getRegistrationNumber())) {
            throw new DuplicateResourceException("Bus", "registrationNumber",
                    requestDTO.getRegistrationNumber());
        }

        Bus bus = new Bus();
        bus.setOffice(office);
        bus.setRegistrationNumber(requestDTO.getRegistrationNumber());
        bus.setCapacity(requestDTO.getCapacity());
        bus.setType(requestDTO.getType());

        Bus savedBus = iBusRepository.save(bus);
        return convertToResponseDTO(savedBus);
    }

    /**
     * Retrieves all buses assigned to a specific agency office.
     * First verifies the office exists, then fetches all buses linked to it.
     *
     * @param officeId - the ID of the office whose buses to retrieve
     * @return List of BusResponse - all buses for that office
     */
    @Override
    public List<BusResponse> getBusesByOffice(Integer officeId) {
        if (!officeRepository.existsById(officeId)) {
            throw new ResourceNotFoundException("AgencyOffice", "officeId", officeId);
        }

        return iBusRepository.findByOffice_OfficeId(officeId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single bus by its unique bus ID.
     * Throws ResourceNotFoundException if no bus exists with that ID.
     *
     * @param busId - the unique ID of the bus to find
     * @return BusResponse - the bus details
     */
    @Override
    public BusResponse getBusById(Integer busId) {
        Bus bus = iBusRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "busId", busId));
        return convertToResponseDTO(bus);
    }

    /**
     * Updates an existing bus's details.
     * Finds the bus by ID, checks for duplicate registration numbers
     * (only if the number is being changed), then updates all fields.
     * This method is transactional to ensure data consistency.
     *
     * @param busId      - the ID of the bus to update
     * @param requestDTO - the new bus data (registrationNumber, capacity, type)
     * @return BusResponse - the updated bus data
     */
    @Override
    @Transactional
    public BusResponse updateBus(Integer busId, BusRequest requestDTO) {
        Bus bus = iBusRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "busId", busId));

        if (!bus.getRegistrationNumber().equals(requestDTO.getRegistrationNumber()) &&
                iBusRepository.existsByRegistrationNumber(requestDTO.getRegistrationNumber())) {
            throw new DuplicateResourceException("Bus", "registrationNumber",
                    requestDTO.getRegistrationNumber());
        }

        bus.setRegistrationNumber(requestDTO.getRegistrationNumber());
        bus.setCapacity(requestDTO.getCapacity());
        bus.setType(requestDTO.getType());

        Bus updatedBus = iBusRepository.save(bus);
        return convertToResponseDTO(updatedBus);
    }

    /**
     * Retires (permanently deletes) a bus from the system.
     * Finds the bus by ID and removes it from the database.
     * Throws ResourceNotFoundException if the bus doesn't exist.
     * This method is transactional to ensure data consistency.
     *
     * @param busId - the ID of the bus to retire
     */
    @Override
    @Transactional
    public void retireBus(Integer busId) {
        Bus bus = iBusRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "busId", busId));
        iBusRepository.delete(bus);
    }

    /**
     * Helper method to convert a Bus entity to a BusResponse DTO.
     * Maps bus ID, office ID, registration number, capacity, and type.
     *
     * @param bus - the Bus entity to convert
     * @return BusResponse - the mapped DTO
     */
    private BusResponse convertToResponseDTO(Bus bus) {
        BusResponse dto = new BusResponse();
        dto.setBusId(bus.getBusId());
        dto.setOfficeId(bus.getOffice().getOfficeId());
        dto.setRegistrationNumber(bus.getRegistrationNumber());
        dto.setCapacity(bus.getCapacity());
        dto.setType(bus.getType());
        return dto;
    }
}