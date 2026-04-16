package com.gemini.BusTicketBookingSystem.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripRequest {

    @NotNull(message = "Route ID is required")
    private Integer routeId;

    @NotNull(message = "Bus ID is required")
    private Integer busId;

    @NotNull(message = "Driver 1 ID is required")
    private Integer driver1Id;

    // Optional (you used it in service → so better validate conditionally later)
    private Integer driver2Id;

    @NotNull(message = "Boarding address ID is required")
    private Integer boardingAddressId;

    @NotNull(message = "Dropping address ID is required")
    private Integer droppingAddressId;

    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be greater than 0")
    private BigDecimal fare;

    @NotNull(message = "Trip date is required")
    @FutureOrPresent(message = "Trip date cannot be in the past")
    private LocalDateTime tripDate;
}