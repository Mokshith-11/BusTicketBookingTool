package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO for creating/updating a bus route.
 * Contains source city, destination city, break points, and duration.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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