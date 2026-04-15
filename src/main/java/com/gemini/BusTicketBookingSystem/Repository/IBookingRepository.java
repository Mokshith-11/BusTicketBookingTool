package com.gemini.BusTicketBookingSystem.Repository;


import com.gemini.BusTicketBookingSystem.Entity.Booking;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByTrip_TripId(Integer tripId);
    List<Booking> findByCustomer_CustomerId(Integer customerId);
    List<Booking> findByTrip_TripIdAndStatus(Integer tripId, BookingStatus status);
    boolean existsByTrip_TripIdAndSeatNumber(Integer tripId, Integer seatNumber);
}