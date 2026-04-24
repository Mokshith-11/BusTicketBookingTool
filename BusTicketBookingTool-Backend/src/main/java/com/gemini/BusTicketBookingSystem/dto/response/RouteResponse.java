package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

/**
 * DTO for returning route data to the client.
 * Contains the route details including cities, stops, and duration.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This response DTO is the safe shape of Route data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
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
