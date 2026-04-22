package com.gemini.BusTicketBookingSystem.dto.request;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyOfficeRequest {

    private Integer agencyId;

    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;

    @jakarta.validation.constraints.NotNull(message = "Office address ID is required")
    private Integer officeAddressId;
}
