package com.gemini.BusTicketBookingSystem.dto.response;




import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;

@Builder

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

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public PaymentResponse(Integer paymentId, Integer bookingId, Integer customerId, String customerName, BigDecimal amount, LocalDateTime paymentDate, PaymentStatus paymentStatus, Integer tripId, Integer seatNumber) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.tripId = tripId;
        this.seatNumber = seatNumber;
    }

    public PaymentResponse() {
    }
}

