package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for creating/updating a trip.
 * Contains all the information needed to define a trip:
 * route, bus, drivers, boarding/dropping addresses, schedule, and fare.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripRequest {

    /** ID of the route this trip follows — required */
    @NotNull(message = "Route ID is required")
    private Integer routeId;

    /** ID of the bus assigned to this trip — required */
    @NotNull(message = "Bus ID is required")
    private Integer busId;

    /** ID of the primary driver — required */
    @NotNull(message = "Driver 1 ID is required")
    private Integer driver1Id;

    /** ID of the secondary driver — optional (for long-distance trips) */
    // Optional (you used it in service → so better validate conditionally later)
    private Integer driver2Id;

    /** ID of the boarding point address — required */
    @NotNull(message = "Boarding address ID is required")
    private Integer boardingAddressId;

    /** ID of the dropping point address — required */
    @NotNull(message = "Dropping address ID is required")
    private Integer droppingAddressId;

    /** Scheduled departure date and time — required, must be in the future */
    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    /** Scheduled arrival date and time — required */
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    /** Ticket fare per seat — required, must be positive */
    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be greater than 0")
    private BigDecimal fare;

    /** Date of the trip — required, cannot be in the past */
    @NotNull(message = "Trip date is required")
    @FutureOrPresent(message = "Trip date cannot be in the past")
    private LocalDate tripDate;

    @AssertTrue(message = "Arrival time must be after departure time")
    public boolean isArrivalAfterDeparture() {
        if (departureTime == null || arrivalTime == null) {
            return true;
        }
        return arrivalTime.isAfter(departureTime);
    }

    @AssertTrue(message = "Trip date must match departure date")
    public boolean isTripDateSameAsDepartureDate() {
        if (departureTime == null || tripDate == null) {
            return true;
        }
        return tripDate.equals(departureTime.toLocalDate());
    }
}
