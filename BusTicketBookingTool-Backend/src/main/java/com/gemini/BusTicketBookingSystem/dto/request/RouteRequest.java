package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO for creating/updating a bus route.
 * Contains source city, destination city, break points, and duration.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This request DTO describes the JSON input required to create or update Route data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class RouteRequest {

    /** Departure city name (e.g., "Chennai") — required */
    @NotBlank(message = "From city is required")
    private String fromCity;

    /** Destination city name (e.g., "Bangalore") — required */
    @NotBlank(message = "To city is required")
    private String toCity;

    /** Number of intermediate stops/break points — optional */
    private Integer breakPoints;

    /** Estimated travel duration in minutes — optional */
    private Integer duration;
}