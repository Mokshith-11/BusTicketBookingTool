package com.gemini.BusTicketBookingSystem;

import com.gemini.BusTicketBookingSystem.Dto.Request.BookingRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.BookingResponse;
import com.gemini.BusTicketBookingSystem.Entity.Booking;
import com.gemini.BusTicketBookingSystem.Entity.Bus;
import com.gemini.BusTicketBookingSystem.Entity.Trip;
import com.gemini.BusTicketBookingSystem.Exception.SeatNotAvailableException;
import com.gemini.BusTicketBookingSystem.Repository.IBookingRepository;
import com.gemini.BusTicketBookingSystem.Repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.Repository.ITripRepository;
import com.gemini.BusTicketBookingSystem.Service.Impl.BookingServiceImpl;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceRepositoryTest {

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private ITripRepository tripRepository;

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Trip trip;
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
        trip.setDepartureTime(LocalDateTime.now().plusHours(2));

        booking = new Booking();
        booking.setBookingId(1);
        booking.setTrip(trip);
        booking.setSeatNumber(5);
        booking.setStatus(BookingStatus.Booked);

        request = new BookingRequest();
        request.setSeatNumber(5);
    }

    @Test
    void testBookSeat_Success() {
        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(bookingRepository.findBookingsByTripIdAndStatus(1, BookingStatus.Booked))
                .thenReturn(Arrays.asList());
        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(booking);

        BookingResponse response = bookingService.bookSeat(1, request);

        assertNotNull(response);
        assertEquals(5, response.getSeatNumber());
        assertEquals("Booked", response.getStatus());

        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void testBookSeat_SeatAlreadyBooked() {
        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(bookingRepository.findBookingsByTripIdAndStatus(1, BookingStatus.Booked))
                .thenReturn(Arrays.asList(booking));

        assertThrows(SeatNotAvailableException.class, () ->
                bookingService.bookSeat(1, request));
    }

    @Test
    void testGetBookingById() {
        when(bookingRepository.findById(1))
                .thenReturn(Optional.of(booking));

        BookingResponse response = bookingService.getBookingById(1);

        assertNotNull(response);
        assertEquals(1, response.getBookingId());
        assertEquals(5, response.getSeatNumber());
    }

    @Test
    void testCancelBooking() {
        when(bookingRepository.findById(1))
                .thenReturn(Optional.of(booking));

        bookingService.cancelBooking(1);

        assertEquals(BookingStatus.Available, booking.getStatus());

        verify(bookingRepository, times(1)).save(booking);
        verify(tripRepository, times(1)).save(trip);
    }

    @Test
    void testGetBookedSeats() {
        when(tripRepository.findById(1))
                .thenReturn(Optional.of(trip));

        when(bookingRepository.findBookingsByTripIdAndStatus(1, BookingStatus.Booked))
                .thenReturn(Arrays.asList(booking));

        var seats = bookingService.getBookedSeats(1);

        assertEquals(1, seats.size());
        assertEquals(5, seats.get(0));
    }
}