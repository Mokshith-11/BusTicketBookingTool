package com.gemini.BusTicketBookingSystem.Exception;


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

    public static class SeatNotAvailableException extends RuntimeException {

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
}