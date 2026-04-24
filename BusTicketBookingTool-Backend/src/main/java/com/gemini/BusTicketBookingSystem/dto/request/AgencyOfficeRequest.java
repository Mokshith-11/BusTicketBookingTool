package com.gemini.BusTicketBookingSystem.dto.request;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This request DTO describes the JSON input required to create or update Agency Office data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class AgencyOfficeRequest {

    private Integer agencyId;

    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;

    @jakarta.validation.constraints.NotNull(message = "Office address ID is required")
    private Integer officeAddressId;
}
