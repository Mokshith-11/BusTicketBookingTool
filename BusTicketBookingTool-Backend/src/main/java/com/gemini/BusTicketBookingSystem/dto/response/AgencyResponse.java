package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyResponse {
    private Integer agencyId;
    private String name;
    private String contactPersonName;
    private String email;
    private String phone;
}