package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyResponse;

import java.util.List;
/*
 * - This service interface lists the Agency actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

/**
 * Service interface for agency management.
 * Defines the contract for CRUD operations on travel agencies.
 * Implemented by AgencyServiceImpl.
 */
public interface IAgencyService {
    /** Creates a new agency and returns the saved data */
    AgencyResponse createAgency(AgencyRequest request);

    /** Retrieves an agency by its unique ID */
    AgencyResponse getAgencyById(Integer agencyId);

    /** Retrieves all agencies from the database */
    List<AgencyResponse> getAllAgencies();

    /** Updates an existing agency with new data */
    AgencyResponse updateAgency(Integer agencyId, AgencyRequest request);

    /** Deletes an agency by its ID */
    void deleteAgency(Integer agencyId);
}
