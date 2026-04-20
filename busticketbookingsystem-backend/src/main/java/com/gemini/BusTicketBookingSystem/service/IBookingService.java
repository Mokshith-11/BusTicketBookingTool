package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.BookingRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BookingResponse;

import java.util.List;

public interface IBookingService {
        BookingResponse bookSeat(Integer tripId, BookingRequest requestDTO);
        List<BookingResponse> getCustomerBookings(Integer customerId);
        BookingResponse getBookingById(Integer bookingId);
        void cancelBooking(Integer bookingId);
        List<Integer> getAvailableSeats(Integer tripId);
        List<Integer> getBookedSeats(Integer tripId);
}

