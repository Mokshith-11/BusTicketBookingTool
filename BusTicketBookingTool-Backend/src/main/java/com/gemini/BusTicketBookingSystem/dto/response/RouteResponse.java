package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * Beginner guide:
 * - This response DTO is the safe shape of Route data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class RouteResponse {


    private Integer routeId;
    private String fromCity;
    private String toCity;
    private Integer breakPoints;
    private Integer duration;
}

