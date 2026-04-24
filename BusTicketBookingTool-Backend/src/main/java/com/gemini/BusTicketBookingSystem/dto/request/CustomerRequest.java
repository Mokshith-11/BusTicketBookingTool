package com.gemini.BusTicketBookingSystem.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for registering/updating a customer.
 * Contains customer details with validation for unique email and phone.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This request DTO describes the JSON input required to create or update Customer data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class CustomerRequest {

    /** Full name of the customer — required */
    @NotBlank(message = "Name is required")
    private String name;

    /** Email address — required, must be valid format, must be unique */
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /** Phone number — required, must be unique */
    @NotBlank(message = "Phone is required")
    private String phone;

    /** ID of the customer's residential address — required */
    @NotNull(message = "Address ID is required")
    private Integer addressId;
}
