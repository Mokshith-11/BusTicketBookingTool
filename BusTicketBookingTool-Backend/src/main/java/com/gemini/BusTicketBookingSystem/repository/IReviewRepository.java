package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/*
 * - This repository is the database access layer for Review records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.trip.tripId = :tripId")
    List<Review> findByTripId(@Param("tripId") Integer tripId);

    @Query("SELECT r FROM Review r WHERE r.customer.customerId = :customerId")
    List<Review> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Review r WHERE r.trip.tripId = :tripId AND r.customer.customerId = :customerId")
    boolean existsByTripAndCustomer(@Param("tripId") Integer tripId,
                                    @Param("customerId") Integer customerId);
}