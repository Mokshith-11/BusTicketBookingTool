package com.gemini.BusTicketBookingSystem.dto.request;

import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor @AllArgsConstructor @Builder


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


    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

     public Integer getBookingId() {
         return bookingId;
     }

     public void setBookingId(Integer bookingId) {
         this.bookingId = bookingId;
     }

     public Integer getCustomerId() {
         return customerId;
     }

     public void setCustomerId(Integer customerId) {
         this.customerId = customerId;
     }

     public BigDecimal getAmount() {
         return amount;
     }

     public void setAmount(BigDecimal amount) {
         this.amount = amount;
     }

     public LocalDateTime getPaymentDate() {
         return paymentDate;
     }

     public void setPaymentDate(LocalDateTime paymentDate) {
         this.paymentDate = paymentDate;
     }

     public PaymentStatus getPaymentStatus() {
         return paymentStatus;
     }

     public void setPaymentStatus(PaymentStatus paymentStatus) {
         this.paymentStatus = paymentStatus;
     }

     public PaymentRequest() {
     }

     public PaymentRequest(Integer bookingId, Integer customerId, BigDecimal amount, LocalDateTime paymentDate, PaymentStatus paymentStatus) {
         this.bookingId = bookingId;
         this.customerId = customerId;
         this.amount = amount;
         this.paymentDate = paymentDate;
         this.paymentStatus = paymentStatus;
     }
 }

