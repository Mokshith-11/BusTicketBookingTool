package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.TripRequest;
import com.gemini.BusTicketBookingSystem.dto.response.TripResponse;

import java.time.LocalDate;
import java.util.List;
/*
 * - This service interface lists the Trip actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface ITripService {
    TripResponse createTrip(TripRequest requestDTO);
    List<TripResponse> getAllTrips();
    TripResponse getTripById(Integer tripId);
    List<TripResponse> searchTrips(String fromCity, String toCity, LocalDate date);
    TripResponse updateTrip(Integer tripId, TripRequest requestDTO);
    void closeTrip(Integer tripId);
}
