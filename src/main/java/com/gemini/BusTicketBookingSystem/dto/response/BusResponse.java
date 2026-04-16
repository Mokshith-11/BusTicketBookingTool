package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BusResponse {
    private Integer busId;
    private Integer officeId;
    private String registrationNumber;
    private Integer capacity;
    private String type;
}

