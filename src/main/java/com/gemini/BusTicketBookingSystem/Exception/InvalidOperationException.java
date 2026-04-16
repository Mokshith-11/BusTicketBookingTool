package com.gemini.BusTicketBookingSystem.Exception;

/**
 * Custom exception thrown when an invalid business operation is attempted.
 * This can happen when:
 * - Attempting to cancel an already cancelled booking
 * - Trying to close a trip that's already closed
 * - Attempting to book a trip that has already departed
 * - Invalid payment status transitions
 */
public class InvalidOperationException extends RuntimeException {

    private String operation;
    private String reason;

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String operation, String reason) {
        super(String.format("Invalid operation '%s': %s", operation, reason));
        this.operation = operation;
        this.reason = reason;
    }

    public String getOperation() {
        return operation;
    }

    public String getReason() {
        return reason;
    }
}