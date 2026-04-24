package com.gemini.BusTicketBookingSystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
/*
 * - This enum limits Payment Status to fixed allowed values instead of free text.
 * - Fixed values make validation, database storage, and frontend display more predictable.
 * - Services compare against these values when deciding whether an operation is allowed.
 */

/**
 * Enum representing the possible statuses of a payment transaction.
 * - Success: Payment was processed successfully
 * - Failed: Payment was rejected or failed to process
 */
public enum PaymentStatus {
    /** Payment completed successfully */
    Success,
    /** Payment failed or was rejected */
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
