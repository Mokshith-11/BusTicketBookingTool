package com.gemini.BusTicketBookingSystem.exceptions;

/**
 * Custom exception thrown when a business operation is not allowed.
 * For example: booking a seat on a departed trip, cancelling an already cancelled booking,
 * or making a payment for an unconfirmed booking.
 * Results in HTTP 400 (Bad Request) response.
 */
public class InvalidOperationException extends RuntimeException {

    /** The operation that was attempted (e.g., "Book Seat", "Cancel Booking") */
    private String operation;

    /** The reason the operation is not allowed */
    private String reason;

    /**
     * Creates an InvalidOperationException with a custom message.
     *
     * @param message - the error message
     */
    public InvalidOperationException(String message) {
        super(message);
    }

    /**
     * Creates an InvalidOperationException with operation name and reason.
     * Example: "Invalid operation 'Book Seat': Cannot book seat for a trip that has already departed"
     *
     * @param operation - the name of the attempted operation
     * @param reason    - why the operation is not allowed
     */
    public InvalidOperationException(String operation, String reason) {
        super(String.format("Invalid operation '%s': %s", operation, reason));
        this.operation = operation;
        this.reason = reason;
    }

    /** Gets the operation name */
    public String getOperation() {
        return operation;
    }

    /** Gets the reason for failure */
    public String getReason() {
        return reason;
    }
}