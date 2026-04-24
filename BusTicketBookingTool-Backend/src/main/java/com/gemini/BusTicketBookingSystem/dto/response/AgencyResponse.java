package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

/**
 * DTO for returning agency data to the client.
 * Contains the agency details that were saved in the database.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This response DTO is the safe shape of Agency data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class AgencyResponse {
    /** Unique agency identifier */
    private Integer agencyId;

    /** Name of the agency */
    private String name;

    /** Name of the primary contact person */
    private String contactPersonName;

    /** Agency email address */
    private String email;

    /** Agency phone number */
    private String phone;
}