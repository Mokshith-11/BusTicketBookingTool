package com.gemini.BusTicketBookingSystem.dto.response;


import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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