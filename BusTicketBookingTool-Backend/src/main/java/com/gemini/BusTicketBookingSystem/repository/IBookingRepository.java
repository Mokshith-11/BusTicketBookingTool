package com.gemini.BusTicketBookingSystem.repository;


import com.gemini.BusTicketBookingSystem.entity.Booking;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing database operations on Booking entities.
 * Extends JpaRepository to get built-in CRUD methods (save, findById, delete, etc.).
 * Contains custom queries for fetching bookings by trip, customer, and status.
 */
@Repository
/*
 * - This repository is the database access layer for Booking records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IBookingRepository extends JpaRepository<Booking, Integer> {

    /**
     * Finds all bookings associated with a specific trip.
     *
     * @param tripId - the trip ID to search by
     * @return List of Booking - all bookings for that trip
     */
    @Query("SELECT b FROM Booking b WHERE b.trip.tripId = :tripId")
    List<Booking> findBookingsByTripId(@Param("tripId") Integer tripId);

    /**
     * Finds all bookings made by a specific customer.
     *
     * @param customerId - the customer ID to search by
     * @return List of Booking - all bookings by that customer
     */
    @Query("SELECT b FROM Booking b WHERE b.customer.customerId = :customerId")
    List<Booking> findBookingsByCustomerId(@Param("customerId") Integer customerId);

    /**
     * Finds all bookings for a trip filtered by a specific booking status.
     * Used to get only "Booked" seats to check availability.
     *
     * @param tripId - the trip ID to search by
     * @param status - the booking status to filter by (e.g., Booked, Available)
     * @return List of Booking - bookings matching both trip and status
     */
    @Query("SELECT b FROM Booking b WHERE b.trip.tripId = :tripId AND b.status = :status")
    List<Booking> findBookingsByTripIdAndStatus(
            @Param("tripId") Integer tripId,
            @Param("status") BookingStatus status
    );

    /**
     * Checks if a booking already exists for a specific seat on a specific trip.
     * Returns true if the seat is taken, false otherwise.
     *
     * @param tripId     - the trip ID to check
     * @param seatNumber - the seat number to check
     * @return boolean - true if a booking exists for that seat on that trip
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b WHERE b.trip.tripId = :tripId AND b.seatNumber = :seatNumber")
    boolean existsBookingByTripIdAndSeatNumber(
            @Param("tripId") Integer tripId,
            @Param("seatNumber") Integer seatNumber
    );
}