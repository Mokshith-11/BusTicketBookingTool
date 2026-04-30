package com.gemini.BusTicketBookingSystem.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private Integer driver2Id;

    @NotNull(message = "Boarding address ID is required")
    private Integer boardingAddressId;

    @NotNull(message = "Dropping address ID is required")
    private Integer droppingAddressId;

    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    @JsonDeserialize(using = UtcLocalDateTimeDeserializer.class)
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @JsonDeserialize(using = UtcLocalDateTimeDeserializer.class)
    private LocalDateTime arrivalTime;

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be greater than 0")
    private BigDecimal fare;

    @NotNull(message = "Trip date is required")
    @FutureOrPresent(message = "Trip date cannot be in the past")
    private LocalDate tripDate;

    // Cross-field validation: arrival must be after departure.
    @AssertTrue(message = "Arrival time must be after departure time")
    public boolean isArrivalAfterDeparture() {
        if (departureTime == null || arrivalTime == null) {
            return true;
        }
        return arrivalTime.isAfter(departureTime);
    }

    // Cross-field validation: tripDate must match the departure date.
    @AssertTrue(message = "Trip date must match departure date")
    public boolean isTripDateSameAsDepartureDate() {
        if (departureTime == null || tripDate == null) {
            return true;
        }
        return tripDate.equals(departureTime.toLocalDate());
    }
}
