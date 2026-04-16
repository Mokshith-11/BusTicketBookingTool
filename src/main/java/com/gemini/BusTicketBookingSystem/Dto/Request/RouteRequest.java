package com.gemini.BusTicketBookingSystem.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RouteRequest {

    @NotBlank(message = "From city is required")
    private String fromCity;

    @NotBlank(message = "To city is required")
    private String toCity;

    private Integer breakPoints;
    private Integer duration;
}