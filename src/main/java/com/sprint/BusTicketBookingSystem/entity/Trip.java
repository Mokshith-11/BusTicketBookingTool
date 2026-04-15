package com.sprint.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripId;

//    // @NotNull means this field cannot be empty
//    @NotNull(message = "Route is required")
//    @ManyToOne
//    @JoinColumn(name = "route_id")
//    private Route route;
//
//    @NotNull(message = "Bus is required")
//    @ManyToOne
//    @JoinColumn(name = "bus_id")
//    private Bus bus;
//
//    @NotNull(message = "Boarding address is required")
//    @ManyToOne
//    @JoinColumn(name = "boarding_address_id")
//    private Address boardingAddress;
//
//    @NotNull(message = "Dropping address is required")
//    @ManyToOne
//    @JoinColumn(name = "dropping_address_id")
//    private Address droppingAddress;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

//    @NotNull(message = "Driver 1 is required")
//    @ManyToOne
//    @JoinColumn(name = "driver1_driver_id")
//    private Driver driver1;
//
//    @NotNull(message = "Driver 2 is required")
//    @ManyToOne
//    @JoinColumn(name = "driver2_driver_id")
//    private Driver driver2;

    @NotNull(message = "Available seats is required")
    private Integer availableSeats;

    @NotNull(message = "Fare is required")
    private BigDecimal fare;

    @NotNull(message = "Trip date is required")
    private LocalDateTime tripDate;
}