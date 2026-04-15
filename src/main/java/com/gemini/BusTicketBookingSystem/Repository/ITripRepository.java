package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ITripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByRoute_FromCityAndRoute_ToCityAndTripDateBetween(
            String fromCity, String toCity,
            LocalDateTime start, LocalDateTime end);
}