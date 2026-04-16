package com.gemini.BusTicketBookingSystem.Service.Impl;

import com.gemini.BusTicketBookingSystem.Entity.Agency;
import com.gemini.BusTicketBookingSystem.Exception.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.Repository.IAgencyRepository;
import com.gemini.BusTicketBookingSystem.Service.IAgencyService;
import com.gemini.BusTicketBookingSystem.Dto.Request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.AgencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements IAgencyService {
@Autowired
    private  IAgencyRepository agencyRepository;

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

    @Override
    public AgencyResponse getAgencyById(Integer agencyId) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency", agencyId));
        return mapToResponse(agency);
    }

    @Override
    public List<AgencyResponse> getAllAgencies() {
        return agencyRepository.findAll()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

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

    @Override
    public void deleteAgency(Integer agencyId) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency", agencyId));
        agencyRepository.delete(agency);
    }

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