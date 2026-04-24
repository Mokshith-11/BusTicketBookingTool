package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.entity.Agency;
import com.gemini.BusTicketBookingSystem.entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.repository.IAgencyRepository;
import com.gemini.BusTicketBookingSystem.service.IAgencyOfficeService;
import com.gemini.BusTicketBookingSystem.dto.request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AddressResponse;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyOfficeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
/*
 * Beginner guide:
 * - This class contains the real business logic for Agency Office operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */

public class AgencyOfficeServiceImpl implements IAgencyOfficeService {
@Autowired
    private  IAgencyOfficeRepository officeRepository;
    @Autowired
    private  IAgencyRepository agencyRepository;
    @Autowired
    private  IAddressesRepository addressRepository;

    @Override
    public AgencyOfficeResponse addOffice(Integer agencyId, AgencyOfficeRequest request) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency", agencyId));
        Addresses address = addressRepository.findById(request.getOfficeAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", request.getOfficeAddressId()));
        AgencyOffice office = AgencyOffice.builder()
                .agency(agency)
                .officeMail(request.getOfficeMail())
                .officeContactPersonName(request.getOfficeContactPersonName())
                .officeContactNumber(request.getOfficeContactNumber())
                .officeAddress(address)
                .build();
        return mapToResponse(officeRepository.save(office));
    }

    @Override
    public AgencyOfficeResponse getOfficeById(Integer officeId) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office", officeId));
        return mapToResponse(office);
    }

    @Override
    public List<AgencyOfficeResponse> getOfficesByAgency(Integer agencyId) {
        agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency", agencyId));
        return officeRepository.findOfficesByAgencyId(agencyId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public AgencyOfficeResponse updateOffice(Integer officeId, AgencyOfficeRequest request) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office", officeId));
        Addresses address = addressRepository.findById(request.getOfficeAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", request.getOfficeAddressId()));
        office.setOfficeMail(request.getOfficeMail());
        office.setOfficeContactPersonName(request.getOfficeContactPersonName());
        office.setOfficeContactNumber(request.getOfficeContactNumber());
        office.setOfficeAddress(address);
        return mapToResponse(officeRepository.save(office));
    }

    @Override
    public void deleteOffice(Integer officeId) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office", officeId));
        officeRepository.delete(office);
    }

    private AgencyOfficeResponse mapToResponse(AgencyOffice office) {
        AddressResponse addressResponse = null;
        if (office.getOfficeAddress() != null) {
            Addresses a = office.getOfficeAddress();
            addressResponse = AddressResponse.builder()
                    .addressId(a.getAddressId())
                    .address(a.getAddress())
                    .city(a.getCity())
                    .state(a.getState())
                    .zipCode(a.getZipCode())
                    .build();
        }
        return AgencyOfficeResponse.builder()
                .officeId(office.getOfficeId())
                .agencyId(office.getAgency().getAgencyId())
                .agencyName(office.getAgency().getName())
                .officeMail(office.getOfficeMail())
                .officeContactPersonName(office.getOfficeContactPersonName())
                .officeContactNumber(office.getOfficeContactNumber())
                .officeAddress(addressResponse)
                .build();
    }
}
