package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.BookingRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BookingResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    @Autowired
    private IBookingService bookingService;


    @PostMapping("/trips/{tripId}/bookings")
    public ResponseEntity<ApiResponse<BookingResponse>> bookSeat(
            @PathVariable Integer tripId,
            @Valid @RequestBody BookingRequest requestDTO) {

        BookingResponse response = bookingService.bookSeat(tripId, requestDTO);

        ApiResponse<BookingResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Booking created successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @GetMapping("/customers/{customerId}/bookings")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getCustomerBookings(
            @PathVariable Integer customerId) {

        List<BookingResponse> bookings = bookingService.getCustomerBookings(customerId);

        ApiResponse<List<BookingResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Customer bookings fetched successfully",
                        bookings);

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(
            @PathVariable Integer bookingId) {

        BookingResponse response = bookingService.getBookingById(bookingId);

        ApiResponse<BookingResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Booking fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    @PatchMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelBooking(
            @PathVariable Integer bookingId) {

        bookingService.cancelBooking(bookingId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Booking cancelled successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }
}
