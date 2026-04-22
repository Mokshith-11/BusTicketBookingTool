package com.gemini.BusTicketBookingSystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for returning trip data to the client.
 * Contains trip details including route info, bus info, schedule, and fare.
 * Date-time fields are formatted for consistent JSON output.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripResponse {

    /** Unique trip identifier */
    private Integer tripId;

    /** ID of the route this trip follows */
    private Integer routeId;

    /** Departure city name */
    private String fromCity;

    /** Destination city name */
    private String toCity;

    /** ID of the bus assigned to this trip */
    private Integer busId;

    /** Registration number of the bus */
    private String busRegistrationNumber;

    /** Scheduled departure time (formatted as "yyyy-MM-dd HH:mm:ss") */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;

    /** Scheduled arrival time (formatted as "yyyy-MM-dd HH:mm:ss") */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;

    /** Number of seats still available for booking */
    private Integer availableSeats;

    /** Ticket fare per seat */
    private BigDecimal fare;

    /** Date of the trip (formatted as "yyyy-MM-dd") */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime tripDate;
}