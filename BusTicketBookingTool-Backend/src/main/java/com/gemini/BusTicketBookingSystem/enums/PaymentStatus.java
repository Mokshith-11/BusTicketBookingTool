package com.gemini.BusTicketBookingSystem.enums;

/**
 * Enum representing the possible statuses of a payment transaction.
 * - Success: Payment was processed successfully
 * - Failed: Payment was rejected or failed to process
 */
public enum PaymentStatus {
    /** Payment completed successfully */
    Success,
    /** Payment failed or was rejected */
    Failed
}