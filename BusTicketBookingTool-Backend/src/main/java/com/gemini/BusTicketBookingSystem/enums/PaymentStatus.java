package com.gemini.BusTicketBookingSystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentStatus {
    Success,
    Failed;

    @JsonCreator
    public static PaymentStatus fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (PaymentStatus status : values()) {
            if (status.name().equalsIgnoreCase(value.trim())) {
                return status;
            }
        }

        throw new IllegalArgumentException("Payment status must be Success or Failed");
    }
}
