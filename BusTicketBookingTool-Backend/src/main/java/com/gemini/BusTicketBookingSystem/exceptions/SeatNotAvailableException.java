package com.gemini.BusTicketBookingSystem.exceptions;
/*
 * Beginner guide:
 * - This custom exception gives the service layer a readable way to report a specific Seat Not Available Exception problem.
 * - Instead of returning random errors, services throw this exception when a known business rule fails.
 * - GlobalExceptionHandler catches it and converts it into a proper JSON error response for the frontend.
 */
public class SeatNotAvailableException extends RuntimeException {

    private Integer tripId;
    private Integer seatNumber;

    public SeatNotAvailableException(String message) {
        super(message);
    }

    public SeatNotAvailableException(Integer tripId, Integer seatNumber) {
        super(String.format("Seat %d is not available for trip %d", seatNumber, tripId));
        this.tripId = tripId;
        this.seatNumber = seatNumber;
    }

    public SeatNotAvailableException(Integer tripId, String message) {
        super(String.format("Seat booking failed for trip %d: %s", tripId, message));
        this.tripId = tripId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }
}