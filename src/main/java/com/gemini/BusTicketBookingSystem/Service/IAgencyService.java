package com.gemini.BusTicketBookingSystem.Service;

import com.gemini.BusTicketBookingSystem.Dto.Request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.AgencyResponse;

import java.util.List;

public interface IAgencyService {
    AgencyResponse createAgency(AgencyRequest request);
    AgencyResponse getAgencyById(Integer agencyId);
    List<AgencyResponse> getAllAgencies();
    AgencyResponse updateAgency(Integer agencyId, AgencyRequest request);
    void deleteAgency(Integer agencyId);
}
