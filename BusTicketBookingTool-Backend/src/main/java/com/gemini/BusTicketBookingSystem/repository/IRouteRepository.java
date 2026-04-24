package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing database operations on Route entities.
 * Extends JpaRepository to get built-in CRUD methods.
 * Contains a custom query to find routes by source and destination cities.
 */
@Repository
/*
 * - This repository is the database access layer for Route records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IRouteRepository extends JpaRepository<Route,Integer> {

    /**
     * Finds all routes that go from a specific city to another.
     * Used to check for duplicate routes (same fromCity-toCity pair).
     *
     * @param fromCity - the departure city name
     * @param toCity   - the destination city name
     * @return List of Route - routes matching the city pair
     */
    @Query("SELECT r FROM Route r WHERE r.fromCity = :fromCity AND r.toCity = :toCity")
    List<Route> findByFromCityAndToCity(@Param("fromCity") String fromCity,
                                        @Param("toCity") String toCity);
}
