package com.gemini.BusTicketBookingSystem.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for registering/updating a bus driver.
 * Contains driver details like license number, name, phone, and address.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This request DTO describes the JSON input required to create or update Driver data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class DriverRequest {

    /** Driving license number — required, must be unique */
    @NotBlank(message = "License number is required")
    private String licenseNumber;

    /** Full name of the driver — required */
    @NotBlank(message = "Name is required")
    private String name;

    /** Phone number of the driver — required */
    @NotBlank(message = "Phone is required")
    private String phone;

    /** ID of the office this driver is assigned to — required */
    @NotNull(message = "Office ID is required")
    private Integer officeId;

    /** ID of the driver's residential address — required */
    @NotNull(message = "Address ID is required")
    private Integer addressId;
}
