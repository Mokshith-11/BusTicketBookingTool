package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This request DTO describes the JSON input required to create or update Route data.
 * - Validation annotations like @NotBlank, @NotNull, @Min, or @Email protect the service from bad input.
 * - Controllers receive this object with @RequestBody and pass the clean data to the service layer.
 */
public class RouteRequest {

    @NotBlank(message = "From city is required")
    private String fromCity;

    @NotBlank(message = "To city is required")
    private String toCity;

    private Integer breakPoints;
    private Integer duration;
}