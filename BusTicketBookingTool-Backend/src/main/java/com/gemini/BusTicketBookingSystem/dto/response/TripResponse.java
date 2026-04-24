package com.gemini.BusTicketBookingSystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 * - This response DTO is the safe shape of Trip data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class TripResponse {

    private Integer tripId;
    private Integer routeId;

    private String fromCity;
    private String toCity;

    private Integer busId;
    private String busRegistrationNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;

    private Integer availableSeats;
    private BigDecimal fare;
    private Boolean closed;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime tripDate;
}
