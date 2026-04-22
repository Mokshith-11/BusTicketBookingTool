package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyOfficeResponse;

import java.util.List;

/**
 * Service interface for agency office management.
 * Defines the contract for CRUD operations on agency offices (branch offices).
 * Implemented by AgencyOfficeServiceImpl.
 */
public interface IAgencyOfficeService {
    /** Adds a new office under a specific agency */
    AgencyOfficeResponse addOffice(Integer agencyId, AgencyOfficeRequest request);

    /** Retrieves an office by its unique ID */
    AgencyOfficeResponse getOfficeById(Integer officeId);

    /** Retrieves all offices belonging to a specific agency */
    List<AgencyOfficeResponse> getOfficesByAgency(Integer agencyId);

    /** Updates an existing office with new data */
    AgencyOfficeResponse updateOffice(Integer officeId, AgencyOfficeRequest request);

    /** Deletes an office by its ID */
    void deleteOffice(Integer officeId);
}
