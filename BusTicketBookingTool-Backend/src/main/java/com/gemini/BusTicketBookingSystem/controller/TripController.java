package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.TripRequest;
import com.gemini.BusTicketBookingSystem.dto.response.TripResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.ITripService;
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
@RequestMapping("/api/v1/trips")
/*
 * - This controller is the API entry point for Trip requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class TripController {

        @Autowired
        private ITripService tripService;

        @Autowired
        private IBookingService bookingService;

        // CREATE TRIP
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
     */
        @PostMapping
        public ResponseEntity<ApiResponse<TripResponse>> createTrip(
                        @Valid @RequestBody TripRequest requestDTO) {

                TripResponse response = tripService.createTrip(requestDTO);

                ApiResponse<TripResponse> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(),
                                "Trip created successfully",
                                response);

                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }

        // GET ALL TRIPS
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
        @GetMapping
        public ResponseEntity<ApiResponse<List<TripResponse>>> getAllTrips() {

                List<TripResponse> trips = tripService.getAllTrips();

                ApiResponse<List<TripResponse>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                                "Trips fetched successfully",
                                trips);

                return ResponseEntity.ok(apiResponse);
        }

        // GET TRIP BY ID
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
        @GetMapping("/{tripId}")
        public ResponseEntity<ApiResponse<TripResponse>> getTripById(
                        @PathVariable Integer tripId) {

                TripResponse response = tripService.getTripById(tripId);

                ApiResponse<TripResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                                "Trip fetched successfully",
                                response);

                return ResponseEntity.ok(apiResponse);
        }

        // SEARCH TRIPS
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
        @GetMapping("/search")
        public ResponseEntity<ApiResponse<List<TripResponse>>> searchTrips(
                        @RequestParam String fromCity,
                        @RequestParam String toCity,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

                List<TripResponse> trips = tripService.searchTrips(fromCity, toCity, date);

                ApiResponse<List<TripResponse>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                                "Trips fetched successfully",
                                trips);

                return ResponseEntity.ok(apiResponse);
        }

        // UPDATE TRIP
    /*
     * PUT flow:
     * - URL gives the record ID and the body gives the new values.
     * - @Valid checks the body, then service finds the old record and updates it.
     * - If the ID does not exist, the service throws ResourceNotFoundException.
     */
        @PutMapping("/{tripId}")
        public ResponseEntity<ApiResponse<TripResponse>> updateTrip(
                        @PathVariable Integer tripId,
                        @Valid @RequestBody TripRequest requestDTO) {

                TripResponse response = tripService.updateTrip(tripId, requestDTO);

                ApiResponse<TripResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                                "Trip updated successfully",
                                response);

                return ResponseEntity.ok(apiResponse);
        }

        // CLOSE TRIP
    /*
     * PATCH flow:
     * - URL points to the existing record and the request changes only one small part, such as status or close action.
     * - Service checks whether the operation is allowed before saving.
     * - This is used when a full update is not needed.
     */
        @PatchMapping("/{tripId}/close")
        public ResponseEntity<ApiResponse<String>> closeTrip(
                        @PathVariable Integer tripId) {

                tripService.closeTrip(tripId);

                ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                                "Trip closed successfully",
                                null);

                return ResponseEntity.ok(apiResponse);
        }

        // SEAT INFO MESSAGE
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
        @GetMapping("/{tripId}/seats")
        public ResponseEntity<ApiResponse<SeatAvailabilityResponse>> getSeatAvailability(
                        @PathVariable Integer tripId) {

                SeatAvailabilityResponse response = new SeatAvailabilityResponse(tripId);

                ApiResponse<SeatAvailabilityResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                                "Seat info fetched",
                                response);

                return ResponseEntity.ok(apiResponse);
        }

        // BOOKED SEATS
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
        @GetMapping("/{tripId}/seats/booked")
        public ResponseEntity<ApiResponse<List<Integer>>> getBookedSeats(
                        @PathVariable Integer tripId) {

                List<Integer> bookedSeats = bookingService.getBookedSeats(tripId);

                ApiResponse<List<Integer>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                                "Booked seats fetched",
                                bookedSeats);

                return ResponseEntity.ok(apiResponse);
        }

        // AVAILABLE SEATS
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
        @GetMapping("/{tripId}/seats/available")
        public ResponseEntity<ApiResponse<List<Integer>>> getAvailableSeats(
                        @PathVariable Integer tripId) {

                List<Integer> availableSeats = bookingService.getAvailableSeats(tripId);

                ApiResponse<List<Integer>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                                "Available seats fetched",
                                availableSeats);

                return ResponseEntity.ok(apiResponse);
        }

        // Inner class
        static class SeatAvailabilityResponse {
                private Integer tripId;
                private String message;

                public SeatAvailabilityResponse(Integer tripId) {
                        this.tripId = tripId;
                        this.message = "Use /seats/available or /seats/booked endpoints";
                }

                public Integer getTripId() {
                        return tripId;
                }

                public String getMessage() {
                        return message;
                }
        }
}
