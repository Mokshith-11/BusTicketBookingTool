package com.gemini.BusTicketBookingSystem.serviceTesting;



import com.gemini.BusTicketBookingSystem.dto.request.BookingRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BookingResponse;
import com.gemini.BusTicketBookingSystem.entity.*;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import com.gemini.BusTicketBookingSystem.exceptions.InvalidOperationException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.exceptions.SeatNotAvailableException;
import com.gemini.BusTicketBookingSystem.repository.IBookingRepository;
import com.gemini.BusTicketBookingSystem.repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.repository.ITripRepository;
import com.gemini.BusTicketBookingSystem.service.impl.BookingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private ITripRepository tripRepository;

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private BookingServiceImpl service;

    private Trip trip;
    private Customer customer;
    private Booking booking;
    private BookingRequest request;

    @BeforeEach
    void setUp() {

        Bus bus = new Bus();
        bus.setCapacity(40);

        trip = new Trip();
        trip.setTripId(1);
        trip.setBus(bus);
        trip.setAvailableSeats(10);
        trip.setDepartureTime(LocalDateTime.now().plusDays(1));

        customer = new Customer();
        customer.setCustomerId(1);

        booking = new Booking();
        booking.setBookingId(1);
        booking.setTrip(trip);
        booking.setCustomer(customer);
        booking.setSeatNumber(5);
        booking.setStatus(BookingStatus.Booked);

        request = new BookingRequest();
        request.setSeatNumber(5);
        request.setCustomerId(1);
    }

    // BOOK SEAT SUCCESS
    @Test
    void testBookSeat_Success() {

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(bookingRepository.findBookingsByTripIdAndStatus(1, BookingStatus.Booked))
                .thenReturn(List.of());
        when(bookingRepository.save(any())).thenReturn(booking);

        BookingResponse response = service.bookSeat(1, request);

        assertNotNull(response);
        assertEquals(5, response.getSeatNumber());

        verify(tripRepository).save(trip);
        verify(bookingRepository).save(any());
    }

    // ❌ TRIP NOT FOUND
    @Test
    void testBookSeat_TripNotFound() {

        when(tripRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.bookSeat(1, request)
        );
    }

    // ❌ SEAT ALREADY BOOKED

    // ❌ CUSTOMER NOT FOUND
    @Test
    void testBookSeat_CustomerNotFound() {

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(bookingRepository.findBookingsByTripIdAndStatus(1, BookingStatus.Booked))
                .thenReturn(List.of());
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.bookSeat(1, request)
        );
    }

    // GET BOOKING BY ID
    @Test
    void testGetBookingById_Success() {

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        BookingResponse response = service.getBookingById(1);

        assertEquals(1, response.getBookingId());

        verify(bookingRepository).findById(1);
    }

    // ❌ GET BOOKING BY ID NOT FOUND
    @Test
    void testGetBookingById_NotFound() {

        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.getBookingById(1)
        );
    }

    // CANCEL BOOKING
    @Test
    void testCancelBooking_Success() {

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        service.cancelBooking(1);

        assertEquals(BookingStatus.Available, booking.getStatus());

        verify(tripRepository).save(trip);
        verify(bookingRepository).save(booking);
    }

    // ❌ CANCEL BOOKING INVALID
    @Test
    void testCancelBooking_Invalid() {

        booking.setStatus(BookingStatus.Available);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        assertThrows(InvalidOperationException.class, () ->
                service.cancelBooking(1)
        );
    }

    // GET AVAILABLE SEATS
    @Test
    void testGetAvailableSeats() {

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(bookingRepository.findBookingsByTripIdAndStatus(1, BookingStatus.Booked))
                .thenReturn(List.of(booking));

        List<Integer> result = service.getAvailableSeats(1);

        assertFalse(result.contains(5));
    }

    // ❌ TRIP NOT FOUND FOR AVAILABLE SEATS
    @Test
    void testGetAvailableSeats_TripNotFound() {

        when(tripRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.getAvailableSeats(1)
        );
    }

    // GET BOOKED SEATS
    @Test
    void testGetBookedSeats() {

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(bookingRepository.findBookingsByTripIdAndStatus(1, BookingStatus.Booked))
                .thenReturn(List.of(booking));

        List<Integer> result = service.getBookedSeats(1);

        assertEquals(1, result.size());
        assertEquals(5, result.get(0));
    }
}
