package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.TripRequest;
import com.gemini.BusTicketBookingSystem.dto.response.TripResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.ITripService;

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
public class TripController {

    @Autowired
    private ITripService tripService;

    // ✅ CREATE TRIP
    @PostMapping
    public ResponseEntity<ApiResponse<TripResponse>> createTrip(
            @Valid @RequestBody TripRequest requestDTO) {

        TripResponse response = tripService.createTrip(requestDTO);

        ApiResponse<TripResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Trip created successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // ✅ GET ALL TRIPS
    @GetMapping
    public ResponseEntity<ApiResponse<List<TripResponse>>> getAllTrips() {

        List<TripResponse> trips = tripService.getAllTrips();

        ApiResponse<List<TripResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Trips fetched successfully",
                        trips);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ GET TRIP BY ID
    @GetMapping("/{tripId}")
    public ResponseEntity<ApiResponse<TripResponse>> getTripById(
            @PathVariable Integer tripId) {

        TripResponse response = tripService.getTripById(tripId);

        ApiResponse<TripResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Trip fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ SEARCH TRIPS
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TripResponse>>> searchTrips(
            @RequestParam String fromCity,
            @RequestParam String toCity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<TripResponse> trips = tripService.searchTrips(fromCity, toCity, date);

        ApiResponse<List<TripResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Trips fetched successfully",
                        trips);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ UPDATE TRIP
    @PutMapping("/{tripId}")
    public ResponseEntity<ApiResponse<TripResponse>> updateTrip(
            @PathVariable Integer tripId,
            @Valid @RequestBody TripRequest requestDTO) {

        TripResponse response = tripService.updateTrip(tripId, requestDTO);

        ApiResponse<TripResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Trip updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ CLOSE TRIP
    @PatchMapping("/{tripId}/close")
    public ResponseEntity<ApiResponse<String>> closeTrip(
            @PathVariable Integer tripId) {

        tripService.closeTrip(tripId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Trip closed successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ SEAT INFO MESSAGE
    @GetMapping("/{tripId}/seats")
    public ResponseEntity<ApiResponse<SeatAvailabilityResponse>> getSeatAvailability(
            @PathVariable Integer tripId) {

        SeatAvailabilityResponse response = new SeatAvailabilityResponse(tripId);

        ApiResponse<SeatAvailabilityResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Seat info fetched",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ BOOKED SEATS
    @GetMapping("/{tripId}/seats/booked")
    public ResponseEntity<ApiResponse<List<Integer>>> getBookedSeats(
            @PathVariable Integer tripId) {

        List<Integer> bookedSeats = List.of(); // replace with service later

        ApiResponse<List<Integer>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Booked seats fetched",
                        bookedSeats);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ AVAILABLE SEATS
    @GetMapping("/{tripId}/seats/available")
    public ResponseEntity<ApiResponse<List<Integer>>> getAvailableSeats(
            @PathVariable Integer tripId) {

        List<Integer> availableSeats = List.of(); // replace with service later

        ApiResponse<List<Integer>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
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

        public Integer getTripId() { return tripId; }
        public String getMessage() { return message; }
    }
}

