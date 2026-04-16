package com.gemini.BusTicketBookingSystem.Repository;

//import com.busticket.entity.Payment;
import com.gemini.BusTicketBookingSystem.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.customer.customerId = :customerId")
    List<Payment> findPaymentsByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
    Optional<Payment> findPaymentByBookingId(@Param("bookingId") Integer bookingId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Payment p WHERE p.booking.bookingId = :bookingId")
    boolean existsPaymentByBookingId(@Param("bookingId") Integer bookingId);

}