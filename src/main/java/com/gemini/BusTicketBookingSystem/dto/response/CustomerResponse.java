package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerResponse {
    private Integer customerId;
    private String name;
    private String email;
    private String phone;


}