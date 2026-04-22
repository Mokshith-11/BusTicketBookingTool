package com.gemini.BusTicketBookingSystem.exceptions;

/**
 * Custom exception thrown when a seat booking cannot be completed.
 * For example: the requested seat is already taken, the seat number is invalid,
 * or there are no available seats left on the trip.
 * Results in HTTP 409 (Conflict) response.
 */
public class SeatNotAvailableException extends RuntimeException {

    /** The trip ID where the seat issue occurred */
    private Integer tripId;

    /** The specific seat number that is not available */
    private Integer seatNumber;

    /**
     * Creates a SeatNotAvailableException with a custom message.
     *
     * @param message - the error message
     */
    public SeatNotAvailableException(String message) {
        super(message);
    }

    /**
     * Creates a SeatNotAvailableException for a specific seat on a specific trip.
     * Example: "Seat 12 is not available for trip 5"
     *
     * @param tripId     - the trip ID
     * @param seatNumber - the unavailable seat number
     */
    public SeatNotAvailableException(Integer tripId, Integer seatNumber) {
        super(String.format("Seat %d is not available for trip %d", seatNumber, tripId));
        this.tripId = tripId;
        this.seatNumber = seatNumber;
    }

    /**
     * Creates a SeatNotAvailableException for a trip with a custom reason.
     * Example: "Seat booking failed for trip 5: No seats available for this trip"
     *
     * @param tripId  - the trip ID
     * @param message - the reason for the failure
     */
    public SeatNotAvailableException(Integer tripId, String message) {
        super(String.format("Seat booking failed for trip %d: %s", tripId, message));
        this.tripId = tripId;
    }

    /** Gets the trip ID */
    public Integer getTripId() {
        return tripId;
    }

    /** Gets the seat number */
    public Integer getSeatNumber() {
        return seatNumber;
    }
}