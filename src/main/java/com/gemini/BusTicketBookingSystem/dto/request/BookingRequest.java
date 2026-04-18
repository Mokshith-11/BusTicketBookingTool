package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingRequest {

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Seat number is required")
    private Integer seatNumber;
}