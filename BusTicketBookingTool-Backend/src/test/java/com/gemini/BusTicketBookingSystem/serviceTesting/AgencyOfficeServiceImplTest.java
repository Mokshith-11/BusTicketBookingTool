package com.gemini.BusTicketBookingSystem.serviceTesting;


import com.gemini.BusTicketBookingSystem.dto.request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyOfficeResponse;
import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.entity.Agency;
import com.gemini.BusTicketBookingSystem.entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.repository.IAgencyRepository;
import com.gemini.BusTicketBookingSystem.service.impl.AgencyOfficeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgencyOfficeServiceImplTest {

    @Mock
    private IAgencyOfficeRepository officeRepository;

    @Mock
    private IAgencyRepository agencyRepository;

    @Mock
    private IAddressesRepository addressRepository;

    @InjectMocks
    private AgencyOfficeServiceImpl service;

    private Agency agency;
    private Addresses address;
    private AgencyOffice office;
    private AgencyOfficeRequest request;

    @BeforeEach
    void setUp() {

        agency = new Agency(1, "ABC Travels", "Ajitha", "abc@gmail.com", "9876543210");

        address = new Addresses(1, "Street 1", "Chennai", "TN", "600001");

        office = AgencyOffice.builder()
                .officeId(1)
                .agency(agency)
                .officeMail("office@abc.com")
                .officeContactPersonName("Ajitha")
                .officeContactNumber("9876543210")
                .officeAddress(address)
                .build();

        request = new AgencyOfficeRequest();
        request.setOfficeMail("office@abc.com");
        request.setOfficeContactPersonName("Ajitha");
        request.setOfficeContactNumber("9876543210");
        request.setOfficeAddressId(1);
    }

    // ADD OFFICE
    @Test
    void testAddOffice_Success() {

        when(agencyRepository.findById(1)).thenReturn(Optional.of(agency));
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(officeRepository.save(any(AgencyOffice.class))).thenReturn(office);

        AgencyOfficeResponse response = service.addOffice(1, request);

        assertNotNull(response);
        assertEquals("ABC Travels", response.getAgencyName());

        verify(agencyRepository).findById(1);
        verify(addressRepository).findById(1);
        verify(officeRepository).save(any(AgencyOffice.class));
    }

    // ❌ ADD OFFICE - AGENCY NOT FOUND
    @Test
    void testAddOffice_AgencyNotFound() {

        when(agencyRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.addOffice(1, request)
        );

        verify(agencyRepository).findById(1);
        verify(addressRepository, never()).findById(any());
        verify(officeRepository, never()).save(any());
    }

    // ❌ ADD OFFICE - ADDRESS NOT FOUND
    @Test
    void testAddOffice_AddressNotFound() {

        when(agencyRepository.findById(1)).thenReturn(Optional.of(agency));
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.addOffice(1, request)
        );

        verify(agencyRepository).findById(1);
        verify(addressRepository).findById(1);
        verify(officeRepository, never()).save(any());
    }

    // GET BY ID
    @Test
    void testGetOfficeById_Success() {

        when(officeRepository.findById(1)).thenReturn(Optional.of(office));

        AgencyOfficeResponse response = service.getOfficeById(1);

        assertEquals("ABC Travels", response.getAgencyName());

        verify(officeRepository).findById(1);
    }

    // ❌ GET BY ID - NOT FOUND
    @Test
    void testGetOfficeById_NotFound() {

        when(officeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.getOfficeById(1)
        );

        verify(officeRepository).findById(1);
    }

    // GET OFFICES BY AGENCY
    @Test
    void testGetOfficesByAgency_Success() {

        when(agencyRepository.findById(1)).thenReturn(Optional.of(agency));
        when(officeRepository.findOfficesByAgencyId(1)).thenReturn(List.of(office));

        List<AgencyOfficeResponse> result = service.getOfficesByAgency(1);

        assertEquals(1, result.size());

        verify(agencyRepository).findById(1);
        verify(officeRepository).findOfficesByAgencyId(1);
    }

    // ❌ GET OFFICES BY AGENCY - AGENCY NOT FOUND
    @Test
    void testGetOfficesByAgency_NotFound() {

        when(agencyRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.getOfficesByAgency(1)
        );

        verify(agencyRepository).findById(1);
        verify(officeRepository, never()).findOfficesByAgencyId(any());
    }

    // UPDATE OFFICE
    @Test
    void testUpdateOffice_Success() {

        when(officeRepository.findById(1)).thenReturn(Optional.of(office));
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(officeRepository.save(any())).thenReturn(office);

        AgencyOfficeResponse response = service.updateOffice(1, request);

        assertEquals("office@abc.com", response.getOfficeMail());

        verify(officeRepository).findById(1);
        verify(addressRepository).findById(1);
        verify(officeRepository).save(any());
    }

    // ❌ UPDATE - OFFICE NOT FOUND
    @Test
    void testUpdateOffice_OfficeNotFound() {

        when(officeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.updateOffice(1, request)
        );

        verify(officeRepository).findById(1);
        verify(officeRepository, never()).save(any());
    }

    // ❌ UPDATE - ADDRESS NOT FOUND
    @Test
    void testUpdateOffice_AddressNotFound() {

        when(officeRepository.findById(1)).thenReturn(Optional.of(office));
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.updateOffice(1, request)
        );

        verify(addressRepository).findById(1);
        verify(officeRepository, never()).save(any());
    }

    // DELETE OFFICE
    @Test
    void testDeleteOffice_Success() {

        when(officeRepository.findById(1)).thenReturn(Optional.of(office));

        service.deleteOffice(1);

        verify(officeRepository).findById(1);
        verify(officeRepository).delete(office);
    }

    // ❌ DELETE OFFICE - NOT FOUND
    @Test
    void testDeleteOffice_NotFound() {

        when(officeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.deleteOffice(1)
        );

        verify(officeRepository).findById(1);
        verify(officeRepository, never()).delete(any());
    }

}



