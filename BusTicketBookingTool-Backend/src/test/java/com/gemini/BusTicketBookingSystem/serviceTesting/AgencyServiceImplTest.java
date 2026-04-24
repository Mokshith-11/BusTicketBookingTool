package com.gemini.BusTicketBookingSystem.serviceTesting;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyResponse;
import com.gemini.BusTicketBookingSystem.entity.Agency;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAgencyRepository;
import com.gemini.BusTicketBookingSystem.service.impl.AgencyServiceImpl;

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
class AgencyServiceImplTest {

    @Mock
    private IAgencyRepository agencyRepository;

    @InjectMocks
    private AgencyServiceImpl agencyService;

    private Agency mockAgency;
    private AgencyRequest mockRequest;

    @BeforeEach
    void setUp() {
        // Mock Entity (DB object)
        mockAgency = new Agency(
                1,
                "ABC Travels",
                "Ajitha",
                "abc@gmail.com",
                "9876543210"
        );

        // Mock Request (Input DTO)
        mockRequest = new AgencyRequest();
        mockRequest.setName("ABC Travels");
        mockRequest.setContactPersonName("Ajitha");
        mockRequest.setEmail("abc@gmail.com");
        mockRequest.setPhone("9876543210");
    }

    // CREATE
    @Test
    void testCreateAgency() {

        when(agencyRepository.save(any(Agency.class))).thenReturn(mockAgency);

        AgencyResponse result = agencyService.createAgency(mockRequest);

        assertNotNull(result);
        assertEquals("ABC Travels", result.getName());

        verify(agencyRepository, times(1)).save(any(Agency.class));
    }

    // GET ALL
    @Test
    void testGetAllAgencies() {

        when(agencyRepository.findAll()).thenReturn(List.of(mockAgency));

        List<AgencyResponse> result = agencyService.getAllAgencies();

        assertEquals(1, result.size());
        assertEquals("ABC Travels", result.get(0).getName());

        verify(agencyRepository, times(1)).findAll();
    }

    // GET BY ID - SUCCESS
    @Test
    void testGetAgencyById_Success() {

        when(agencyRepository.findById(1)).thenReturn(Optional.of(mockAgency));

        AgencyResponse result = agencyService.getAgencyById(1);

        assertEquals("ABC Travels", result.getName());

        verify(agencyRepository, times(1)).findById(1);
    }

    // ❌ GET BY ID - NOT FOUND
    @Test
    void testGetAgencyById_NotFound() {

        when(agencyRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                agencyService.getAgencyById(1)
        );

        verify(agencyRepository, times(1)).findById(1);
    }

    // UPDATE - SUCCESS
    @Test
    void testUpdateAgency_Success() {

        when(agencyRepository.findById(1)).thenReturn(Optional.of(mockAgency));
        when(agencyRepository.save(any(Agency.class))).thenReturn(mockAgency);

        AgencyResponse result = agencyService.updateAgency(1, mockRequest);

        assertEquals("ABC Travels", result.getName());

        verify(agencyRepository, times(1)).findById(1);
        verify(agencyRepository, times(1)).save(any(Agency.class));
    }

    // ❌ UPDATE - NOT FOUND
    @Test
    void testUpdateAgency_NotFound() {

        when(agencyRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                agencyService.updateAgency(1, mockRequest)
        );

        verify(agencyRepository, times(1)).findById(1);
        verify(agencyRepository, never()).save(any());
    }

    // DELETE - SUCCESS
    @Test
    void testDeleteAgency_Success() {

        when(agencyRepository.findById(1)).thenReturn(Optional.of(mockAgency));

        agencyService.deleteAgency(1);

        verify(agencyRepository, times(1)).findById(1);
        verify(agencyRepository, times(1)).delete(mockAgency);
    }

    // ❌ DELETE - NOT FOUND
    @Test
    void testDeleteAgency_NotFound() {

        when(agencyRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                agencyService.deleteAgency(1)
        );

        verify(agencyRepository, times(1)).findById(1);
        verify(agencyRepository, never()).delete(any());
    }
}