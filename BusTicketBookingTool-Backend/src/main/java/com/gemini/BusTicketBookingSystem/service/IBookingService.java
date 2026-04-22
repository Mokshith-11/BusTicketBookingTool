package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.BookingRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BookingResponse;

import java.util.List;

/**
 * Service interface for booking management.
 * Defines the contract for booking seats, viewing bookings,
 * cancelling bookings, and checking seat availability.
 * Implemented by BookingServiceImpl.
 */
public interface IBookingService {
        /** Books a seat on a specific trip for a customer */
        BookingResponse bookSeat(Integer tripId, BookingRequest requestDTO);

        /** Retrieves all bookings made by a specific customer */
        List<BookingResponse> getCustomerBookings(Integer customerId);

        /** Retrieves a booking by its unique ID */
        BookingResponse getBookingById(Integer bookingId);

        /** Cancels a booking and frees up the seat */
        void cancelBooking(Integer bookingId);

        /** Returns list of available (unbooked) seat numbers for a trip */
        List<Integer> getAvailableSeats(Integer tripId);

        /** Returns list of booked seat numbers for a trip */
        List<Integer> getBookedSeats(Integer tripId);
}
