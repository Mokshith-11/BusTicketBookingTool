package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Builder
public class BookingRequest {

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Seat number is required")
    private Integer seatNumber;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}