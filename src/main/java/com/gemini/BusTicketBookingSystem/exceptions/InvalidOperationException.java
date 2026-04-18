package com.gemini.BusTicketBookingSystem.exceptions;
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