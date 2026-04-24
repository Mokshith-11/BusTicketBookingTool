package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

/**
 * DTO for returning bus data to the client.
 * Contains bus details including registration, capacity, and type.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This response DTO is the safe shape of Bus data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class BusResponse {
    /** Unique bus identifier */
    private Integer busId;

    /** ID of the office this bus belongs to */
    private Integer officeId;

    /** Vehicle registration number */
    private String registrationNumber;

    /** Total number of seats */
    private Integer capacity;

    /** Type of bus (e.g., "AC Sleeper") */
    private String type;
}
