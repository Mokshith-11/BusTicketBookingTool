package com.gemini.BusTicketBookingSystem.repository;


import com.gemini.BusTicketBookingSystem.entity.Booking;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/*
 * - This repository is the database access layer for Booking records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IBookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.trip.tripId = :tripId")
    List<Booking> findBookingsByTripId(@Param("tripId") Integer tripId);

    @Query("SELECT b FROM Booking b WHERE b.customer.customerId = :customerId")
    List<Booking> findBookingsByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT b FROM Booking b WHERE b.trip.tripId = :tripId AND b.status = :status")
    List<Booking> findBookingsByTripIdAndStatus(
            @Param("tripId") Integer tripId,
            @Param("status") BookingStatus status
    );

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b WHERE b.trip.tripId = :tripId AND b.seatNumber = :seatNumber")
    boolean existsBookingByTripIdAndSeatNumber(
            @Param("tripId") Integer tripId,
            @Param("seatNumber") Integer seatNumber
    );
}