package com.gemini.BusTicketBookingSystem.dto.request;

import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for making a payment for a booking.
 * Contains the booking ID, customer ID, payment amount, and status.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequest {

    /** ID of the booking to pay for — required */
    @NotNull(message = "Booking ID is required")
    private Integer bookingId;

    /** ID of the customer making the payment — required */
    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    /** Payment amount (must match the trip fare) — required */
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    /** Date/time of the payment — auto-set by the service, not required from client */
    private LocalDateTime paymentDate;

    /** Payment status (Success or Failed) — required */
    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
}