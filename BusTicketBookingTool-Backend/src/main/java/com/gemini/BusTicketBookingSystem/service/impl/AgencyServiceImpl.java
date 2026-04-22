package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.Agency;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAgencyRepository;
import com.gemini.BusTicketBookingSystem.service.IAgencyService;
import com.gemini.BusTicketBookingSystem.dto.request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing travel agencies.
 * Contains business logic for CRUD operations on agency records.
 */
@Service

public class AgencyServiceImpl implements IAgencyService {
@Autowired
    private  IAgencyRepository agencyRepository;

    /**
     * Creates a new travel agency in the database.
     * Builds an Agency entity from the request and saves it.
     *
     * @param request - contains name, contactPersonName, email, phone
     * @return AgencyResponse - the saved agency data with generated ID
     */
    @Override
    public AgencyResponse createAgency(AgencyRequest request) {
        Agency agency = Agency.builder()
                .name(request.getName())
                .contactPersonName(request.getContactPersonName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        return mapToResponse(agencyRepository.save(agency));
    }

    /**
     * Retrieves a single agency by its ID.
     * Throws ResourceNotFoundException if no agency exists with the given ID.
     *
     * @param agencyId - the unique ID of the agency to find
     * @return AgencyResponse - the agency data
     */
    @Override
    public AgencyResponse getAgencyById(Integer agencyId) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency", agencyId));
        return mapToResponse(agency);
    }

    /**
     * Retrieves all agencies stored in the database.
     * Maps each Agency entity to an AgencyResponse DTO.
     *
     * @return List of AgencyResponse - all agencies
     */
    @Override
    public List<AgencyResponse> getAllAgencies() {
        return agencyRepository.findAll()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Updates an existing agency's details.
     * Finds the agency by ID, updates name, contact person, email, and phone,
     * then saves it back to the database.
     * Throws ResourceNotFoundException if the agency doesn't exist.
     *
     * @param agencyId - the ID of the agency to update
     * @param request  - the new agency data
     * @return AgencyResponse - the updated agency data
     */
    @Override
    public AgencyResponse updateAgency(Integer agencyId, AgencyRequest request) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency", agencyId));
        agency.setName(request.getName());
        agency.setContactPersonName(request.getContactPersonName());
        agency.setEmail(request.getEmail());
        agency.setPhone(request.getPhone());
        return mapToResponse(agencyRepository.save(agency));
    }

    /**
     * Deletes an agency from the database by its ID.
     * Throws ResourceNotFoundException if the agency doesn't exist.
     *
     * @param agencyId - the ID of the agency to delete
     */
    @Override
    public void deleteAgency(Integer agencyId) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency", agencyId));
        agencyRepository.delete(agency);
    }

    /**
     * Helper method to convert an Agency entity to an AgencyResponse DTO.
     * Maps all fields from the entity to the response object.
     *
     * @param agency - the Agency entity to convert
     * @return AgencyResponse - the mapped DTO
     */
    private AgencyResponse mapToResponse(Agency agency) {
        return AgencyResponse.builder()
                .agencyId(agency.getAgencyId())
                .name(agency.getName())
                .contactPersonName(agency.getContactPersonName())
                .email(agency.getEmail())
                .phone(agency.getPhone())
                .build();
    }
}