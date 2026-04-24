package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * Beginner guide:
 * - This response DTO is the safe shape of Agency Office data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class AgencyOfficeResponse {
    private Integer officeId;
    private Integer agencyId;
    private String agencyName;
    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;
    private AddressResponse officeAddress;
}