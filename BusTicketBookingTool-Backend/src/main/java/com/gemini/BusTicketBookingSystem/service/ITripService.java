package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.TripRequest;
import com.gemini.BusTicketBookingSystem.dto.response.TripResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for trip management.
 * Defines the contract for creating, viewing, searching, updating,
 * and closing trips. A trip is the core unit that customers book seats on.
 * Implemented by TripServiceImpl.
 */
public interface ITripService {
    /** Creates a new trip with route, bus, drivers, and schedule */
    TripResponse createTrip(TripRequest requestDTO);

    /** Retrieves all trips from the database */
    List<TripResponse> getAllTrips();

    /** Retrieves a trip by its unique ID */
    TripResponse getTripById(Integer tripId);

    /** Searches for trips by departure city, destination city, and date */
    List<TripResponse> searchTrips(String fromCity, String toCity, LocalDate date);

    /** Updates an existing trip with new data */
    TripResponse updateTrip(Integer tripId, TripRequest requestDTO);

    /** Closes a trip so no more bookings can be made */
    void closeTrip(Integer tripId);
}
