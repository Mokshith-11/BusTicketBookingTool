package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO (Data Transfer Object) for creating/updating an address.
 * Used to receive address data from the client in request bodies.
 * All fields are required and validated with @NotBlank.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 * - This request DTO describes the JSON input required to create or update Address data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class AddressRequest {

    /** Street address line (e.g., "123 MG Road") — required */
    @NotBlank(message = "Address is required")
    private String address;

    /** City name (e.g., "Chennai") — required */
    @NotBlank(message = "City is required")
    private String city;

    /** State name (e.g., "Tamil Nadu") — required */
    @NotBlank(message = "State is required")
    private String state;

    /** Zip/Postal code (e.g., "600001") — required */
    @NotBlank(message = "Zip code is required")
    private String zipCode;
}
