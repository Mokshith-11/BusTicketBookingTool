package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.BookingRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BookingResponse;

import java.util.List;
/*
 * Beginner guide:
 * - This service interface lists the Booking actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface IBookingService {
        BookingResponse bookSeat(Integer tripId, BookingRequest requestDTO);
        List<BookingResponse> getCustomerBookings(Integer customerId);
        BookingResponse getBookingById(Integer bookingId);
        void cancelBooking(Integer bookingId);
        List<Integer> getAvailableSeats(Integer tripId);
        List<Integer> getBookedSeats(Integer tripId);
}

