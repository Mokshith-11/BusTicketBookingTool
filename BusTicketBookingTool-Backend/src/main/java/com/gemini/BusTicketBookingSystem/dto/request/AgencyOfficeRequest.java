package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyOfficeRequest {

    private Integer agencyId;

    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;

    @NotNull(message = "Office address ID is required")
    private Integer officeAddressId;
}
