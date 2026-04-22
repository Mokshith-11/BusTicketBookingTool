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

/**
 * Service implementation for managing agency offices (branch offices).
 * Contains business logic for adding, retrieving, updating,
 * and deleting offices that belong to a travel agency.
 */
@Service

public class AgencyOfficeServiceImpl implements IAgencyOfficeService {
@Autowired
    private  IAgencyOfficeRepository officeRepository;
    @Autowired
    private  IAgencyRepository agencyRepository;
    @Autowired
    private  IAddressesRepository addressRepository;

    /**
     * Adds a new office to an existing agency.
     * First verifies that both the agency and the address exist,
     * then creates the office and links it to the agency and address.
     *
     * @param agencyId - the ID of the agency this office belongs to
     * @param request  - office details: officeMail, contactPersonName, contactNumber, addressId
     * @return AgencyOfficeResponse - the saved office data with generated ID
     */
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

    /**
     * Retrieves a single office by its unique ID.
     * Throws ResourceNotFoundException if the office doesn't exist.
     *
     * @param officeId - the unique ID of the office to find
     * @return AgencyOfficeResponse - the office data including agency info and address
     */
    @Override
    public AgencyOfficeResponse getOfficeById(Integer officeId) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office", officeId));
        return mapToResponse(office);
    }

    /**
     * Retrieves all offices belonging to a specific agency.
     * First verifies the agency exists, then fetches all its offices.
     *
     * @param agencyId - the ID of the agency whose offices to retrieve
     * @return List of AgencyOfficeResponse - all offices for that agency
     */
    @Override
    public List<AgencyOfficeResponse> getOfficesByAgency(Integer agencyId) {
        agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency", agencyId));
        return officeRepository.findOfficesByAgencyId(agencyId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Updates an existing office's details.
     * Finds the office by ID, verifies the new address exists,
     * then updates mail, contact person, contact number, and address.
     *
     * @param officeId - the ID of the office to update
     * @param request  - the new office data
     * @return AgencyOfficeResponse - the updated office data
     */
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

    /**
     * Deletes an office from the database by its ID.
     * Throws ResourceNotFoundException if the office doesn't exist.
     *
     * @param officeId - the ID of the office to delete
     */
    @Override
    public void deleteOffice(Integer officeId) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office", officeId));
        officeRepository.delete(office);
    }

    /**
     * Helper method to convert an AgencyOffice entity to an AgencyOfficeResponse DTO.
     * Also maps the nested address entity to an AddressResponse if the address exists.
     *
     * @param office - the AgencyOffice entity to convert
     * @return AgencyOfficeResponse - the mapped DTO with agency and address info
     */
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
