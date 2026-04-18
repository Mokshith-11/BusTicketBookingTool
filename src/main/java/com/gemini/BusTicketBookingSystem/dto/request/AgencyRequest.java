package com.gemini.BusTicketBookingSystem.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyRequest {

    @NotBlank(message = "Agency name is required")
    private String name;

    @NotBlank(message = "Contact person name is required")
    private String contactPersonName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;
}