package com.gemini.BusTicketBookingSystem.Service;

import com.gemini.BusTicketBookingSystem.Dto.Request.BookingRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.BookingResponse;

import java.util.List;

public interface IBookingService {
        BookingResponse bookSeat(Integer tripId, BookingRequest requestDTO);
        List<BookingResponse> getCustomerBookings(Integer customerId);
        BookingResponse getBookingById(Integer bookingId);
        void cancelBooking(Integer bookingId);
        List<Integer> getAvailableSeats(Integer tripId);
        List<Integer> getBookedSeats(Integer tripId);
}

