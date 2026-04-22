package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

/**
 * DTO for returning bus data to the client.
 * Contains bus details including registration, capacity, and type.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
