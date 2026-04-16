package com.gemini.BusTicketBookingSystem;

import com.gemini.BusTicketBookingSystem.entity.Booking;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingRepositoryTest {

    @Test
    void testBookingCreation() {
        Booking booking = new Booking();

        booking.setBookingId(1);
        booking.setSeatNumber(5);
        booking.setStatus(BookingStatus.Booked);

        assertEquals(1, booking.getBookingId());
        assertEquals(5, booking.getSeatNumber());
        assertEquals(BookingStatus.Booked, booking.getStatus());
    }
}