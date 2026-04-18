package com.gemini.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
 @Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Integer tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarding_address_id", nullable = false)
    private Addresses boardingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dropping_address_id", nullable = false)
    private Addresses  droppingAddress;

    @NotNull(message = "Departure time is required")
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver1_driver_id", nullable = false)
    private Driver driver1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver2_driver_id", nullable = true)
    private Driver driver2;

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

    public Driver getDriver1() {
        return driver1;
    }

    public void setDriver1(Driver driver1) {
        this.driver1 = driver1;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Addresses getBoardingAddress() {
        return boardingAddress;
    }

    public void setBoardingAddress(Addresses boardingAddress) {
        this.boardingAddress = boardingAddress;
    }

    public Addresses getDroppingAddress() {
        return droppingAddress;
    }

    public void setDroppingAddress(Addresses droppingAddress) {
        this.droppingAddress = droppingAddress;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Driver getDriver2() {
        return driver2;
    }

    public void setDriver2(Driver driver2) {
        this.driver2 = driver2;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public LocalDateTime getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDateTime tripDate) {
        this.tripDate = tripDate;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Trip() {
    }

    public Trip(Integer tripId, Route route, Bus bus, Addresses boardingAddress, Addresses droppingAddress, LocalDateTime departureTime, LocalDateTime arrivalTime, Driver driver1, Driver driver2, Integer availableSeats, BigDecimal fare, LocalDateTime tripDate, Boolean isClosed) {
        this.tripId = tripId;
        this.route = route;
        this.bus = bus;
        this.boardingAddress = boardingAddress;
        this.droppingAddress = droppingAddress;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.driver1 = driver1;
        this.driver2 = driver2;
        this.availableSeats = availableSeats;
        this.fare = fare;
        this.tripDate = tripDate;
        this.isClosed = isClosed;
    }
}