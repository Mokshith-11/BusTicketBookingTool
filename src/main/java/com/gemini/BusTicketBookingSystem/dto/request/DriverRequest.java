package com.gemini.BusTicketBookingSystem.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DriverRequest {

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Office ID is required")
    private Integer officeId;

    @NotNull(message = "Address ID is required")
    private Integer addressId;
}