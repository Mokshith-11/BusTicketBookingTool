package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for performing database operations on Trip entities.
 * Extends JpaRepository to get built-in CRUD methods.
 * Contains a custom query for searching trips by route and date.
 */
@Repository
/*
 * - This repository is the database access layer for Trip records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface ITripRepository extends JpaRepository<Trip, Integer> {

    /**
     * Searches for trips that match a specific route (fromCity to toCity)
     * and fall within a date range. Used by customers to find available
     * buses for their travel date.
     *
     * @param fromCity - departure city name
     * @param toCity   - destination city name
     * @param start    - start of the date range (typically start of day)
     * @param end      - end of the date range (typically end of day)
     * @return List of Trip - trips matching the route and date criteria
     */
    @Query("SELECT t FROM Trip t " +
            "WHERE t.route.fromCity = :fromCity " +
            "AND t.route.toCity = :toCity " +
            "AND t.tripDate BETWEEN :start AND :end")
    List<Trip> findTripsByCitiesAndDateRange(
            @Param("fromCity") String fromCity,
            @Param("toCity") String toCity,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}