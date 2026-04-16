package com.gemini.BusTicketBookingSystem.Dto.Request;

import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequest {

    @NotNull(message = "Booking ID is required")
    private Integer bookingId;

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private LocalDateTime paymentDate;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
}