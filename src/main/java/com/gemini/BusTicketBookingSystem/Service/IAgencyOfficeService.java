package com.gemini.BusTicketBookingSystem.Service;

import com.gemini.BusTicketBookingSystem.Dto.Request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.AgencyOfficeResponse;

import java.util.List;

public interface IAgencyOfficeService {
    AgencyOfficeResponse addOffice(Integer agencyId, AgencyOfficeRequest request);
    AgencyOfficeResponse getOfficeById(Integer officeId);
    List<AgencyOfficeResponse> getOfficesByAgency(Integer agencyId);
    AgencyOfficeResponse updateOffice(Integer officeId, AgencyOfficeRequest request);
    void deleteOffice(Integer officeId);
}
