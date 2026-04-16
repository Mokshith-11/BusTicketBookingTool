package com.gemini.BusTicketBookingSystem.Dto.Response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyOfficeResponse {
    private Integer officeId;
    private Integer agencyId;
    private String agencyName;
    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;
    private AddressResponse officeAddress;
}