package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyResponse;

import java.util.List;
/*
 * - This service interface lists the Agency actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface IAgencyService {
    AgencyResponse createAgency(AgencyRequest request);
    AgencyResponse getAgencyById(Integer agencyId);
    List<AgencyResponse> getAllAgencies();
    AgencyResponse updateAgency(Integer agencyId, AgencyRequest request);
    void deleteAgency(Integer agencyId);
}
