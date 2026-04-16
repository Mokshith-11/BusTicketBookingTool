package com.gemini.BusTicketBookingSystem.Dto.Response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BusResponse {
    private Integer busId;
    private Integer officeId;
    private String registrationNumber;
    private Integer capacity;
    private String type;
}

