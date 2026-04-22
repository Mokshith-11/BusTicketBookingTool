package com.gemini.BusTicketBookingSystem.dto.response;


import lombok.*;

/**
 * DTO for returning address data to the client.
 * Contains the address details that were saved in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
    /** Unique address identifier */
    private Integer addressId;

    /** Street address line */
    private String address;

    /** City name */
    private String city;

    /** State name */
    private String state;

    /** Zip/Postal code */
    private String zipCode;
}
