package com.gemini.BusTicketBookingSystem.Exception;

/**
 * Custom exception thrown when attempting to book a seat that is not available.
 * This can happen when:
 * - The seat is already booked by another customer
 * - The trip has no available seats left
 * - The seat number is invalid for the given bus
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