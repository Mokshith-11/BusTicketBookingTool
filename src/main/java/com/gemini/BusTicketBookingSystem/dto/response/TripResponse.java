package com.gemini.BusTicketBookingSystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime tripDate;
}