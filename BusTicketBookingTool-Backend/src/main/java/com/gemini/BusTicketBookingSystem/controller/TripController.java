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

/**
 * REST Controller for managing trips and seat availability.
 * Handles creating trips, searching trips by route and date,
 * viewing trip details, closing trips, and checking seat availability.
 * Base URL: /api/v1/trips
 */
@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

	@Autowired
	private ITripService tripService;

	@Autowired
	private IBookingService bookingService;

	/**
	 * Creates a new trip.
	 * A trip connects a route with a bus and drivers. It requires a route, bus,
	 * at least one driver, boarding/dropping addresses, departure/arrival times,
	 * fare, and trip date. Available seats are auto-set to the bus capacity.
	 *
	 * @param requestDTO - trip details including routeId, busId, driverIds, etc.
	 * @return ResponseEntity with HTTP 201 (Created) and the created trip data
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

	/**
	 * Retrieves all trips in the system.
	 * Returns every trip stored in the database, regardless of status or date.
	 *
	 * @return ResponseEntity with HTTP 200 (OK) and a list of all trips
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<List<TripResponse>>> getAllTrips() {

		List<TripResponse> trips = tripService.getAllTrips();

		ApiResponse<List<TripResponse>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
				"Trips fetched successfully",
				trips);

		return ResponseEntity.ok(apiResponse);
	}

	/**
	 * Retrieves a single trip by its unique trip ID.
	 * If the trip is not found, a ResourceNotFoundException is thrown.
	 *
	 * @param tripId - the unique identifier of the trip to retrieve
	 * @return ResponseEntity with HTTP 200 (OK) and the trip details
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

	/**
	 * Searches for trips by source city, destination city, and travel date.
	 * This is the primary search endpoint used by customers to find available trips.
	 * Filters trips that match the given fromCity, toCity, and trip date.
	 *
	 * @param fromCity - the departure city name
	 * @param toCity   - the destination city name
	 * @param date     - the travel date (format: yyyy-MM-dd)
	 * @return ResponseEntity with HTTP 200 (OK) and a list of matching trips
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

	/**
	 * Updates an existing trip's details.
	 * Finds the trip by ID and updates its route, bus, departure/arrival times,
	 * fare, and trip date. Does not update driver assignments.
	 *
	 * @param tripId     - the unique ID of the trip to update
	 * @param requestDTO - the new trip details (validated with @Valid)
	 * @return ResponseEntity with HTTP 200 (OK) and the updated trip data
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

	/**
	 * Closes a trip so no more bookings can be made.
	 * Sets the available seats to 0, effectively preventing further bookings.
	 * Throws ResourceNotFoundException if the trip doesn't exist.
	 *
	 * @param tripId - the unique ID of the trip to close
	 * @return ResponseEntity with HTTP 200 (OK) and a success message
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

	/**
	 * Returns a message directing the user to use the specific seat endpoints.
	 * This is an informational endpoint that tells the user to use
	 * /seats/available or /seats/booked for actual seat data.
	 *
	 * @param tripId - the trip ID for seat information
	 * @return ResponseEntity with HTTP 200 (OK) and a guidance message
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

	/**
	 * Retrieves the list of seat numbers that are currently booked on a trip.
	 * Returns only seat numbers with "Booked" status for the given trip.
	 * Useful for showing which seats are taken on the seat selection UI.
	 *
	 * @param tripId - the ID of the trip to check booked seats for
	 * @return ResponseEntity with HTTP 200 (OK) and a list of booked seat numbers
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

	/**
	 * Retrieves the list of seat numbers that are still available on a trip.
	 * Calculates available seats by excluding booked seats from the bus capacity.
	 * Useful for showing which seats can be selected on the seat selection UI.
	 *
	 * @param tripId - the ID of the trip to check available seats for
	 * @return ResponseEntity with HTTP 200 (OK) and a list of available seat numbers
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

	/**
	 * Inner helper class used to return a guidance message for the /seats endpoint.
	 * Contains the tripId and a message directing users to the correct sub-endpoints.
	 */
	static class SeatAvailabilityResponse {
		private Integer tripId;
		private String message;

		/**
		 * Constructor that sets the tripId and a default guidance message.
		 *
		 * @param tripId - the trip ID this response is for
		 */
		public SeatAvailabilityResponse(Integer tripId) {
			this.tripId = tripId;
			this.message = "Use /seats/available or /seats/booked endpoints";
		}

		/** Returns the trip ID */
		public Integer getTripId() {
			return tripId;
		}

		/** Returns the guidance message */
		public String getMessage() {
			return message;
		}
	}
}
