package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

/**
 * DTO for returning route data to the client.
 * Contains the route details including cities, stops, and duration.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RouteResponse {

    /** Unique route identifier */
    private Integer routeId;

    /** Departure city name */
    private String fromCity;

    /** Destination city name */
    private String toCity;

    /** Number of intermediate stops */
    private Integer breakPoints;

    /** Estimated travel duration in minutes */
    private Integer duration;
}
