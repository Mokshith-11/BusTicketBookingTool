package com.gemini.BusTicketBookingSystem.Dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyOfficeRequest {

    @NotNull(message = "Agency ID is required")
    private Integer agencyId;

    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;

    @NotNull(message = "Office address ID is required")
    private Integer officeAddressId;
}
