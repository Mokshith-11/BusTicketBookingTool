package com.gemini.BusTicketBookingSystem.controller;


import com.gemini.BusTicketBookingSystem.dto.request.BookingRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BookingResponse;
import com.gemini.BusTicketBookingSystem.service.IBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @PostMapping("/trips/{tripId}/bookings")
    public ResponseEntity<BookingResponse> bookSeat(
            @PathVariable Integer tripId,
            @Valid @RequestBody BookingRequest requestDTO) {
        BookingResponse response = bookingService.bookSeat(tripId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{customerId}/bookings")
    public ResponseEntity<List<BookingResponse>> getCustomerBookings(@PathVariable Integer customerId) {
        List<BookingResponse> bookings = bookingService.getCustomerBookings(customerId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Integer bookingId) {
        BookingResponse response = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Integer bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok().build();
    }
}