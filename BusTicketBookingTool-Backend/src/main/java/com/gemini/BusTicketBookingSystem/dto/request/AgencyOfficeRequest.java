package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for creating/updating an agency office (branch office).
 * Contains office details like email, contact info, and address reference.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyOfficeRequest {

    /** ID of the parent agency — required */
    @NotNull(message = "Agency ID is required")
    private Integer agencyId;

    /** Email address of this office — optional */
    private String officeMail;

    /** Name of the contact person at this office — optional */
    private String officeContactPersonName;

    /** Contact phone number for this office — optional */
    private String officeContactNumber;

    /** ID of the physical address for this office — required */
    @NotNull(message = "Office address ID is required")
    private Integer officeAddressId;
}
