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

/**
 * REST Controller for managing seat bookings on trips.
 * Allows customers to book seats, view their bookings, and cancel bookings.
 * Base URL: /api/v1
 */
@RestController
@RequestMapping("/api/v1")
public class BookingController {

    @Autowired
    private IBookingService bookingService;


    /**
     * Books a seat on a specific trip for a customer.
     * Takes the trip ID from the URL and the booking details (customer ID, seat number)
     * from the request body. Validates seat availability before confirming the booking.
     * Throws SeatNotAvailableException if the seat is already taken or invalid.
     *
     * @param tripId     - the ID of the trip to book a seat on
     * @param requestDTO - booking details containing customerId and seatNumber
     * @return ResponseEntity with HTTP 201 (Created) status and the booking confirmation
     */
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


    /**
     * Retrieves all bookings made by a specific customer.
     * Uses the customer ID to look up all their past and current bookings.
     * Throws ResourceNotFoundException if the customer doesn't exist.
     *
     * @param customerId - the ID of the customer whose bookings to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and a list of the customer's bookings
     */
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


    /**
     * Retrieves a single booking by its unique booking ID.
     * If the booking is not found, a ResourceNotFoundException is thrown.
     *
     * @param bookingId - the unique identifier of the booking to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and the booking details
     */
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


    /**
     * Cancels an existing booking by its ID.
     * Changes the booking status from "Booked" to "Available" and restores
     * the seat back to the trip's available seats count.
     * Throws InvalidOperationException if the booking is already cancelled.
     *
     * @param bookingId - the unique ID of the booking to cancel
     * @return ResponseEntity with HTTP 200 (OK) and a cancellation success message
     */
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
