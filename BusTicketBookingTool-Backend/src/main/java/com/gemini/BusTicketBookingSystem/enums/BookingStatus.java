package com.gemini.BusTicketBookingSystem.enums;
/*
 * - This enum limits Booking Status to fixed allowed values instead of free text.
 * - Fixed values make validation, database storage, and frontend display more predictable.
 * - Services compare against these values when deciding whether an operation is allowed.
 */

public enum BookingStatus {
    Available,
    Booked
}

