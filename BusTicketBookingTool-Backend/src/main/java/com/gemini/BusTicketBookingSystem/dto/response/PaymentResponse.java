package com.gemini.BusTicketBookingSystem.dto.response;


import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * Beginner guide:
 * - This response DTO is the safe shape of Payment data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class PaymentResponse {
    private Integer paymentId;
    private Integer bookingId;
    private Integer customerId;
    private String customerName;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;
    private Integer tripId;
    private Integer seatNumber;

}