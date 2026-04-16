package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.TripRequest;
import com.gemini.BusTicketBookingSystem.dto.response.TripResponse;

import java.time.LocalDate;
import java.util.List;

public interface ITripService {
    TripResponse createTrip(TripRequest requestDTO);
    List<TripResponse> getAllTrips();
    TripResponse getTripById(Integer tripId);
    List<TripResponse> searchTrips(String fromCity, String toCity, LocalDate date);
    TripResponse updateTrip(Integer tripId, TripRequest requestDTO);
    void closeTrip(Integer tripId);
}
