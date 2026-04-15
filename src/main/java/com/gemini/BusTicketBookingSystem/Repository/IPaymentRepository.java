package com.sprint.busticketbooking.repository;

//import com.busticket.entity.Payment;
import com.sprint.busticketbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomer_CustomerId(Integer customerId);
    Optional<Payment> findByBooking_BookingId(Integer bookingId);
}