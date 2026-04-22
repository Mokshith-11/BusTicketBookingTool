package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

/**
 * DTO for returning agency office data to the client.
 * Contains office details including parent agency info and nested address.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyOfficeResponse {
    /** Unique office identifier */
    private Integer officeId;

    /** ID of the parent agency */
    private Integer agencyId;

    /** Name of the parent agency */
    private String agencyName;

    /** Email address of this office */
    private String officeMail;

    /** Contact person's name at this office */
    private String officeContactPersonName;

    /** Contact phone number for this office */
    private String officeContactNumber;

    /** Nested address details for this office */
    private AddressResponse officeAddress;
}