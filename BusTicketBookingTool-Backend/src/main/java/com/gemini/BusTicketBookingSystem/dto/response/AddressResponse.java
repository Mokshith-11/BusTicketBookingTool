package com.gemini.BusTicketBookingSystem.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 * - This response DTO is the safe shape of Address data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class AddressResponse {
    private Integer addressId;
    private String address;
    private String city;
    private String state;
    private String zipCode;
}
