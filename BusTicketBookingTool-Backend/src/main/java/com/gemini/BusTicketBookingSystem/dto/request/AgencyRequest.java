package com.gemini.BusTicketBookingSystem.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO for creating/updating a travel agency.
 * Contains agency details with validation constraints.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This request DTO describes the JSON input required to create or update Agency data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class AgencyRequest {

    /** Name of the agency (e.g., "VRL Travels") — required */
    @NotBlank(message = "Agency name is required")
    private String name;

    /** Name of the primary contact person — required */
    @NotBlank(message = "Contact person name is required")
    private String contactPersonName;

    /** Agency email address — required, must be valid email format */
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /** Agency phone number — required */
    @NotBlank(message = "Phone is required")
    private String phone;
}