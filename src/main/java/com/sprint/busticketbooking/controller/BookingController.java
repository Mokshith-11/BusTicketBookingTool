package com.sprint.busticketbooking.controller;

import com.sprint.busticketbooking.entity.Booking;
import com.sprint.busticketbooking.repository.BookingRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // CREATE
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        booking.setBookingStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    // READ ALL
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        booking.setBookingId(id);
        booking.setBookingStatus("UPDATED");
        return bookingRepository.save(booking);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }
}