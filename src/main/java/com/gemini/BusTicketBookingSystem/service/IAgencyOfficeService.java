package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyOfficeResponse;

import java.util.List;

public interface IAgencyOfficeService {
    AgencyOfficeResponse addOffice(Integer agencyId, AgencyOfficeRequest request);
    AgencyOfficeResponse getOfficeById(Integer officeId);
    List<AgencyOfficeResponse> getOfficesByAgency(Integer agencyId);
    AgencyOfficeResponse updateOffice(Integer officeId, AgencyOfficeRequest request);
    void deleteOffice(Integer officeId);
}
