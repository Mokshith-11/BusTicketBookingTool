package com.gemini.BusTicketBookingSystem.dto.request;

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
// TripRequest describes the data the frontend must send to create or update a trip.
// Bean Validation annotations stop invalid requests before service logic starts.
/*
 * - This request DTO describes the JSON input required to create or update Trip data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class TripRequest {

    // @NotNull means the request is rejected if this field is missing.
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

    // @Future makes sure a new trip cannot be scheduled in the past.
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
    private LocalDate tripDate;

    // @AssertTrue is used for cross-field validation when one field depends on another.
    @AssertTrue(message = "Arrival time must be after departure time")
    public boolean isArrivalAfterDeparture() {
        if (departureTime == null || arrivalTime == null) {
            return true;
        }
        return arrivalTime.isAfter(departureTime);
    }

    // The tripDate shown in the UI must match the actual date part of departureTime.
    @AssertTrue(message = "Trip date must match departure date")
    public boolean isTripDateSameAsDepartureDate() {
        if (departureTime == null || tripDate == null) {
            return true;
        }
        return tripDate.equals(departureTime.toLocalDate());
    }
}
