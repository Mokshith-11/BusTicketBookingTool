package com.gemini.BusTicketBookingSystem.Service.Impl;

import com.gemini.BusTicketBookingSystem.Entity.Addresses;
import com.gemini.BusTicketBookingSystem.Entity.Agency;
import com.gemini.BusTicketBookingSystem.Entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.Exception.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.Repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.Repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.Repository.IAgencyRepository;
import com.gemini.BusTicketBookingSystem.Service.IAgencyOfficeService;
import com.gemini.BusTicketBookingSystem.Dto.Request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.AddressResponse;
import com.gemini.BusTicketBookingSystem.Dto.Response.AgencyOfficeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgencyOfficeServiceImpl implements IAgencyOfficeService {
@Autowired
    private  IAgencyOfficeRepository officeRepository;
    private  IAgencyRepository agencyRepository;
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
