package com.gemini.BusTicketBookingSystem.serviceTesting;

import com.gemini.BusTicketBookingSystem.dto.request.BusRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BusResponse;
import com.gemini.BusTicketBookingSystem.entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.entity.Bus;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.repository.IBusRepository;
import com.gemini.BusTicketBookingSystem.service.impl.BusServiceImpl;

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
class BusServiceImplTest {

    @Mock
    private IBusRepository iBusRepository;

    @Mock
    private IAgencyOfficeRepository officeRepository;

    @InjectMocks
    private BusServiceImpl busService;

    private AgencyOffice office;
    private Bus bus;
    private BusRequest request;

    @BeforeEach
    void setup() {
        office = new AgencyOffice();
        office.setOfficeId(1);

        bus = new Bus();
        bus.setBusId(1);
        bus.setOffice(office);
        bus.setRegistrationNumber("TN01AB1234");
        bus.setCapacity(40);
        bus.setType("AC");

        request = new BusRequest();
        request.setRegistrationNumber("TN01AB1234");
        request.setCapacity(40);
        request.setType("AC");
    }

    // =============================
    // CREATE
    // =============================





    // =============================
    // READ
    // =============================
    @Test
    void testGetBusById_Success() {

        when(iBusRepository.findById(1)).thenReturn(Optional.of(bus));

        BusResponse response = busService.getBusById(1);

        assertNotNull(response);
        assertEquals(1, response.getBusId());
    }

    @Test
    void testGetBusById_NotFound() {

        when(iBusRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> busService.getBusById(1));
    }





    // =============================
    // UPDATE
    // =============================
    @Test
    void testUpdateBus_Success() {

        when(iBusRepository.findById(1)).thenReturn(Optional.of(bus));

        // same registration → no duplicate check triggered
        when(iBusRepository.save(any(Bus.class))).thenReturn(bus);

        BusResponse response = busService.updateBus(1, request);

        assertNotNull(response);
        assertEquals("TN01AB1234", response.getRegistrationNumber());

        verify(iBusRepository).save(any(Bus.class));
    }

    @Test
    void testUpdateBus_NotFound() {

        when(iBusRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> busService.updateBus(1, request));
    }

    @Test
    void testUpdateBus_Duplicate() {

        Bus existingBus = new Bus();
        existingBus.setBusId(1);
        existingBus.setRegistrationNumber("OLD123");

        when(iBusRepository.findById(1)).thenReturn(Optional.of(existingBus));
        when(iBusRepository.existsByRegistrationNumber(request.getRegistrationNumber()))
                .thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> busService.updateBus(1, request));
    }

    // =============================
    // DELETE
    // =============================
    @Test
    void testRetireBus_Success() {

        when(iBusRepository.findById(1)).thenReturn(Optional.of(bus));

        busService.retireBus(1);

        verify(iBusRepository).delete(bus);
    }

    @Test
    void testRetireBus_NotFound() {

        when(iBusRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> busService.retireBus(1));
    }
}