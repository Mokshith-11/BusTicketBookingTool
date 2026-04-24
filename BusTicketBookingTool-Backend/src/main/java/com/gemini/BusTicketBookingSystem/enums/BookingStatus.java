package com.gemini.BusTicketBookingSystem.enums;
/*
 * - This enum limits Booking Status to fixed allowed values instead of free text.
 * - Fixed values make validation, database storage, and frontend display more predictable.
 * - Services compare against these values when deciding whether an operation is allowed.
 */

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
