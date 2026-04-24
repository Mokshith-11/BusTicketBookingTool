package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
/*
 * Beginner guide:
 * - This repository is the database access layer for Trip records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface ITripRepository extends JpaRepository<Trip, Integer> {
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