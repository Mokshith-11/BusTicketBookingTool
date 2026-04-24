package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyOfficeResponse;

import java.util.List;
/*
 * Beginner guide:
 * - This service interface lists the Agency Office actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface IAgencyOfficeService {
    AgencyOfficeResponse addOffice(Integer agencyId, AgencyOfficeRequest request);
    AgencyOfficeResponse getOfficeById(Integer officeId);
    List<AgencyOfficeResponse> getOfficesByAgency(Integer agencyId);
    AgencyOfficeResponse updateOffice(Integer officeId, AgencyOfficeRequest request);
    void deleteOffice(Integer officeId);
}
