package com.gemini.BusTicketBookingSystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
/*
 * Beginner guide:
 * - This enum limits Payment Status to fixed allowed values instead of free text.
 * - Fixed values make validation, database storage, and frontend display more predictable.
 * - Services compare against these values when deciding whether an operation is allowed.
 */

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
