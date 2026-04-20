package com.gemini.BusTicketBookingSystem.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
    private Integer addressId;
    private String address;
    private String city;
    private String state;
    private String zipCode;
}
