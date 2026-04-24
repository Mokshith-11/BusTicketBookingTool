package com.gemini.BusTicketBookingSystem.repository;

//import com.busticket.entity.Payment;
import com.gemini.BusTicketBookingSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
/*
 * - This repository is the database access layer for Payment records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.customer.customerId = :customerId")
    List<Payment> findPaymentsByCustomerId(@Param("customerId") Integer customerId);

    Optional<Payment> findFirstByBooking_BookingIdOrderByPaymentIdDesc(Integer bookingId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Payment p WHERE p.booking.bookingId = :bookingId")
    boolean existsPaymentByBookingId(@Param("bookingId") Integer bookingId);

}
