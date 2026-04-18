package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripRequest {

    @NotNull(message = "Route ID is required")
    private Integer routeId;

    @NotNull(message = "Bus ID is required")
    private Integer busId;

    @NotNull(message = "Driver 1 ID is required")
    private Integer driver1Id;

    // Optional (you used it in service → so better validate conditionally later)
    private Integer driver2Id;

    @NotNull(message = "Boarding address ID is required")
    private Integer boardingAddressId;

    @NotNull(message = "Dropping address ID is required")
    private Integer droppingAddressId;

    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be greater than 0")
    private BigDecimal fare;

    @NotNull(message = "Trip date is required")
    @FutureOrPresent(message = "Trip date cannot be in the past")
    private LocalDate tripDate;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getDriver1Id() {
        return driver1Id;
    }

    public void setDriver1Id(Integer driver1Id) {
        this.driver1Id = driver1Id;
    }

    public Integer getDriver2Id() {
        return driver2Id;
    }

    public void setDriver2Id(Integer driver2Id) {
        this.driver2Id = driver2Id;
    }

    public Integer getBoardingAddressId() {
        return boardingAddressId;
    }

    public void setBoardingAddressId(Integer boardingAddressId) {
        this.boardingAddressId = boardingAddressId;
    }

    public Integer getDroppingAddressId() {
        return droppingAddressId;
    }

    public void setDroppingAddressId(Integer droppingAddressId) {
        this.droppingAddressId = droppingAddressId;
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

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }
}