package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.TripRequest;
import com.gemini.BusTicketBookingSystem.dto.response.TripResponse;
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

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@Valid @RequestBody TripRequest requestDTO) {
        TripResponse response = tripService.createTrip(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getAllTrips() {
        List<TripResponse> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponse> getTripById(@PathVariable Integer tripId) {
        TripResponse response = tripService.getTripById(tripId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TripResponse>> searchTrips(
            @RequestParam String fromCity,
            @RequestParam String toCity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TripResponse> trips = tripService.searchTrips(fromCity, toCity, date);
        return ResponseEntity.ok(trips);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripResponse> updateTrip(
            @PathVariable Integer tripId,
            @Valid @RequestBody TripRequest requestDTO) {
        TripResponse response = tripService.updateTrip(tripId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{tripId}/close")
    public ResponseEntity<Void> closeTrip(@PathVariable Integer tripId) {
        tripService.closeTrip(tripId);
        return ResponseEntity.ok().build();
    }

    // Seat availability endpoints
    @GetMapping("/{tripId}/seats")
    public ResponseEntity<SeatAvailabilityResponse> getSeatAvailability(@PathVariable Integer tripId) {
        return ResponseEntity.ok(new SeatAvailabilityResponse(tripId));
    }

    @GetMapping("/{tripId}/seats/booked")
    public ResponseEntity<List<Integer>> getBookedSeats(@PathVariable Integer tripId) {
        // This would be handled by BookingService
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{tripId}/seats/available")
    public ResponseEntity<List<Integer>> getAvailableSeats(@PathVariable Integer tripId) {
        // This would be handled by BookingService
        return ResponseEntity.ok(List.of());
    }

    // Inner class for seat availability response
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
 