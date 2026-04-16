package com.gemini.BusTicketBookingSystem.Service;

import com.gemini.BusTicketBookingSystem.Dto.Request.TripRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.TripResponse;

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
