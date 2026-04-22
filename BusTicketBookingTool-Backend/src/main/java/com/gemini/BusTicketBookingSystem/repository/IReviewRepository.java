package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing database operations on Review entities.
 * Extends JpaRepository to get built-in CRUD methods.
 * Contains custom queries for finding reviews by trip, customer,
 * and checking for duplicate reviews.
 */
@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {

    /**
     * Finds all reviews written for a specific trip.
     *
     * @param tripId - the trip ID to search by
     * @return List of Review - all reviews for that trip
     */
    @Query("SELECT r FROM Review r WHERE r.trip.tripId = :tripId")
    List<Review> findByTripId(@Param("tripId") Integer tripId);

    /**
     * Finds all reviews written by a specific customer.
     *
     * @param customerId - the customer ID to search by
     * @return List of Review - all reviews by that customer
     */
    @Query("SELECT r FROM Review r WHERE r.customer.customerId = :customerId")
    List<Review> findByCustomerId(@Param("customerId") Integer customerId);

    /**
     * Checks if a customer has already reviewed a specific trip.
     * Used to prevent duplicate reviews (one review per customer per trip).
     *
     * @param tripId     - the trip ID to check
     * @param customerId - the customer ID to check
     * @return boolean - true if the customer has already reviewed the trip
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Review r WHERE r.trip.tripId = :tripId AND r.customer.customerId = :customerId")
    boolean existsByTripAndCustomer(@Param("tripId") Integer tripId,
                                    @Param("customerId") Integer customerId);
}