package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/*
 * - This repository is the database access layer for Route records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IRouteRepository extends JpaRepository<Route,Integer> {
    @Query("SELECT r FROM Route r WHERE r.fromCity = :fromCity AND r.toCity = :toCity")
    List<Route> findByFromCityAndToCity(@Param("fromCity") String fromCity,
                                        @Param("toCity") String toCity);
}
