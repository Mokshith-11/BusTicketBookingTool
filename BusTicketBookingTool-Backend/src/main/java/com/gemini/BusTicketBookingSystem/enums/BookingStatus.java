package com.gemini.BusTicketBookingSystem.enums;

/**
 * Enum representing the possible statuses of a seat booking.
 * - Available: The seat is free / booking has been cancelled
 * - Booked: The seat has been reserved by a customer
 */
public enum BookingStatus {
    /** Seat is free and can be booked */
    Available,
    /** Seat is reserved by a customer */
    Booked
}
