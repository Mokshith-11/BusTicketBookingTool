package com.gemini.BusTicketBookingSystem.dto.request;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BusRequest {

    @NotNull(message = "Office ID is required")
    private Integer officeId;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotBlank(message = "Bus type is required")
    private String type;
}
