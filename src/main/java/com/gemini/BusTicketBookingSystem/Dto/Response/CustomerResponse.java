package com.gemini.BusTicketBookingSystem.Dto.Response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerResponse {
    private Integer customerId;
    private String name;
    private String email;
    private String phone;
//    private AddressResponse address;
}