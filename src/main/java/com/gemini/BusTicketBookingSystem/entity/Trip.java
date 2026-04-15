package com.gemini.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Integer tripId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "route_id", nullable = false)
//    private Route route;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "bus_id", nullable = false)
//    private Bus bus;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "boarding_address_id", nullable = false)
//    private Address boardingAddress;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "dropping_address_id", nullable = false)
//    private Address droppingAddress;

    @NotNull(message = "Departure time is required")
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "driver1_driver_id", nullable = false)
//    private Driver driver1;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "driver2_driver_id", nullable = false)
//    private Driver driver2;

    @NotNull(message = "Available seats is required")
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @NotNull(message = "Fare is required")
    @Column(name = "fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal fare;

    @NotNull(message = "Trip date is required")
    @Column(name = "trip_date", nullable = false)
    private LocalDateTime tripDate;

    @Column(name = "is_closed")
    private Boolean isClosed = false;
}