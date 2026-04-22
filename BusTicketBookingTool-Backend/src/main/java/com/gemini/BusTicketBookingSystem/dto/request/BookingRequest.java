package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for booking a seat on a trip.
 * Contains the customer ID and the desired seat number.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingRequest {

    /** ID of the customer making the booking — required */
    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    /** Seat number to book (1 to bus capacity) — required */
    @NotNull(message = "Seat number is required")
    private Integer seatNumber;
}