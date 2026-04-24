package com.gemini.BusTicketBookingSystem.exceptions;
/*
 * Beginner guide:
 * - This custom exception gives the service layer a readable way to report a specific nvalid Operation Exception problem.
 * - Instead of returning random errors, services throw this exception when a known business rule fails.
 * - GlobalExceptionHandler catches it and converts it into a proper JSON error response for the frontend.
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