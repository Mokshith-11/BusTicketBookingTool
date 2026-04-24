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

@Service
/*
 * Beginner guide:
 * - This class contains the real business logic for Agency operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */

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