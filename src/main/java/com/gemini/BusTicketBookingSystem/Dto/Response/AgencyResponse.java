package com.gemini.BusTicketBookingSystem.Dto.Response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyResponse {
    private Integer agencyId;
    private String name;
    private String contactPersonName;
    private String email;
    private String phone;
}