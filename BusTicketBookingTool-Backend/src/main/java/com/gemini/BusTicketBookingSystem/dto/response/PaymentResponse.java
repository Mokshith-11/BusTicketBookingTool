package com.gemini.BusTicketBookingSystem.dto.response;


import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for returning payment data to the client.
 * Contains payment details including booking info, customer info, and payment status.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentResponse {
    /** Unique payment identifier */
    private Integer paymentId;

    /** ID of the booking this payment is for */
    private Integer bookingId;

    /** ID of the customer who made the payment */
    private Integer customerId;

    /** Name of the customer who made the payment */
    private String customerName;

    /** Payment amount */
    private BigDecimal amount;

    /** Date and time when the payment was processed */
    private LocalDateTime paymentDate;

    /** Current payment status (Success or Failed) */
    private PaymentStatus paymentStatus;

    /** ID of the trip associated with the paid booking */
    private Integer tripId;

    /** Seat number from the paid booking */
    private Integer seatNumber;

}