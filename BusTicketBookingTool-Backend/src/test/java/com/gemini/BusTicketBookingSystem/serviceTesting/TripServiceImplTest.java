package com.gemini.BusTicketBookingSystem.serviceTesting;



import com.gemini.BusTicketBookingSystem.dto.request.TripRequest;
import com.gemini.BusTicketBookingSystem.dto.response.TripResponse;
import com.gemini.BusTicketBookingSystem.entity.*;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.*;
import com.gemini.BusTicketBookingSystem.service.impl.TripServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {

    @Mock private ITripRepository tripRepository;
    @Mock private IRouteRepository routeRepository;
    @Mock private IBusRepository busRepository;
    @Mock private IDriverRepositorty driverRepository;
    @Mock private IAddressesRepository addressRepository;

    @InjectMocks
    private TripServiceImpl tripService;

    // -----------------------------
    // MOCK DATA
    // -----------------------------
    private Route mockRoute() {
        Route r = new Route();
        r.setRouteId(1);
        r.setFromCity("Chennai");
        r.setToCity("Bangalore");
        return r;
    }

    private Bus mockBus() {
        Bus b = new Bus();
        b.setBusId(1);
        b.setCapacity(40);
        b.setRegistrationNumber("TN01AB1234");
        return b;
    }

    private Driver mockDriver(int id) {
        Driver d = new Driver();
        d.setDriverId(id);
        return d;
    }

    private Addresses mockAddress(int id) {
        Addresses a = new Addresses();
        a.setAddressId(id);
        return a;
    }

    private Trip mockTrip() {
        Trip t = new Trip();
        t.setTripId(1);
        t.setRoute(mockRoute());
        t.setBus(mockBus());
        t.setAvailableSeats(40);

        t.setTripDate(LocalDateTime.now());
        return t;
    }

    private TripRequest mockRequest() {
        TripRequest req = new TripRequest();
        req.setRouteId(1);
        req.setBusId(1);
        req.setDriver1Id(1);
        req.setDriver2Id(2);
        req.setBoardingAddressId(1);
        req.setDroppingAddressId(2);
        req.setDepartureTime(LocalDateTime.now());
        req.setArrivalTime(LocalDateTime.now().plusHours(6));

        req.setTripDate(LocalDate.now());
        return req;
    }

    // -----------------------------
    // CREATE
    // -----------------------------
    @Test
    void createTrip_success() {
        TripRequest req = mockRequest();

        when(routeRepository.findById(1)).thenReturn(Optional.of(mockRoute()));
        when(busRepository.findById(1)).thenReturn(Optional.of(mockBus()));
        when(driverRepository.findById(1)).thenReturn(Optional.of(mockDriver(1)));
        when(driverRepository.findById(2)).thenReturn(Optional.of(mockDriver(2)));
        when(addressRepository.findById(1)).thenReturn(Optional.of(mockAddress(1)));
        when(addressRepository.findById(2)).thenReturn(Optional.of(mockAddress(2)));
        when(tripRepository.save(any(Trip.class))).thenReturn(mockTrip());

        TripResponse response = tripService.createTrip(req);

        assertNotNull(response);
        assertEquals(40, response.getAvailableSeats());
        verify(tripRepository).save(any(Trip.class));
    }

    @Test
    void createTrip_routeNotFound() {
        when(routeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tripService.createTrip(mockRequest()));
    }

    // -----------------------------
    // GET ALL
    // -----------------------------
    @Test
    void getAllTrips_success() {
        when(tripRepository.findAll()).thenReturn(List.of(mockTrip()));

        List<TripResponse> list = tripService.getAllTrips();

        assertEquals(1, list.size());
        verify(tripRepository).findAll();
    }

    // -----------------------------
    // GET BY ID
    // -----------------------------
    @Test
    void getTripById_success() {
        when(tripRepository.findById(1)).thenReturn(Optional.of(mockTrip()));

        TripResponse response = tripService.getTripById(1);

        assertEquals(1, response.getTripId());
    }

    @Test
    void getTripById_notFound() {
        when(tripRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tripService.getTripById(1));
    }

    // -----------------------------
    // UPDATE

    @Test
    void updateTrip_notFound() {
        when(tripRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tripService.updateTrip(1, mockRequest()));
    }

    // -----------------------------
    // DELETE (closeTrip)
    // -----------------------------
    @Test
    void closeTrip_success() {
        Trip trip = mockTrip();

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));

        tripService.closeTrip(1);

        assertEquals(0, trip.getAvailableSeats());
        verify(tripRepository).save(trip);
    }

    @Test
    void closeTrip_notFound() {
        when(tripRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tripService.closeTrip(1));
    }

    // -----------------------------
    // SEARCH
    // -----------------------------
    @Test
    void searchTrips_success() {
        when(tripRepository.findTripsByCitiesAndDateRange(
                eq("Chennai"),
                eq("Bangalore"),
                any(),
                any()
        )).thenReturn(List.of(mockTrip()));

        List<TripResponse> result =
                tripService.searchTrips("Chennai", "Bangalore", LocalDate.now());

        assertEquals(1, result.size());
    }
}
