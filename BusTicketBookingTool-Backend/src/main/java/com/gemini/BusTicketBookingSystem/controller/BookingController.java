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
/*
 * Beginner guide:
 * - This controller is the API entry point for Booking requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class BookingController {

    @Autowired
    private IBookingService bookingService;
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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
    /*
     * PATCH flow:
     * - URL points to the existing record and the request changes only one small part, such as status or close action.
     * - Service checks whether the operation is allowed before saving.
     * - This is used when a full update is not needed.
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
