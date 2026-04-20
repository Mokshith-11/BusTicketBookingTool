package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyResponse;

import java.util.List;

public interface IAgencyService {
    AgencyResponse createAgency(AgencyRequest request);
    AgencyResponse getAgencyById(Integer agencyId);
    List<AgencyResponse> getAllAgencies();
    AgencyResponse updateAgency(Integer agencyId, AgencyRequest request);
    void deleteAgency(Integer agencyId);
}
