package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing database operations on Payment entities.
 * Extends JpaRepository to get built-in CRUD methods.
 * Contains custom queries for finding payments by customer and booking.
 */
@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

    /**
     * Finds all payments made by a specific customer.
     *
     * @param customerId - the customer ID to search by
     * @return List of Payment - all payments by that customer
     */
    @Query("SELECT p FROM Payment p WHERE p.customer.customerId = :customerId")
    List<Payment> findPaymentsByCustomerId(@Param("customerId") Integer customerId);

    /**
     * Finds the payment associated with a specific booking.
     * Returns Optional because a booking might not have a payment yet.
     *
     * @param bookingId - the booking ID to search by
     * @return Optional of Payment - the payment if it exists
     */
    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
    Optional<Payment> findPaymentByBookingId(@Param("bookingId") Integer bookingId);

    /**
     * Checks if a payment already exists for a specific booking.
     * Used to prevent duplicate payments for the same booking.
     *
     * @param bookingId - the booking ID to check
     * @return boolean - true if a payment exists for that booking
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Payment p WHERE p.booking.bookingId = :bookingId")
    boolean existsPaymentByBookingId(@Param("bookingId") Integer bookingId);

}