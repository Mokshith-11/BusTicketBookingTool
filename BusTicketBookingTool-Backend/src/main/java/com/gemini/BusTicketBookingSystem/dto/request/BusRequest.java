package com.gemini.BusTicketBookingSystem.dto.request;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for registering/updating a bus.
 * Contains bus details like registration number, capacity, and type.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This request DTO describes the JSON input required to create or update Bus data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class BusRequest {

    /** ID of the office this bus belongs to — required */
    @NotNull(message = "Office ID is required")
    private Integer officeId;

    /** Vehicle registration number (e.g., "TN01AB1234") — required, must be unique */
    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    /** Total number of seats in the bus — required, minimum 1 */
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    /** Type of bus (e.g., "AC Sleeper", "Non-AC Seater") — required */
    @NotBlank(message = "Bus type is required")
    private String type;
}
