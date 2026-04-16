package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.dto.response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;

import java.util.List;

public interface IPaymentService {
        PaymentResponse makePayment(PaymentRequest requestDTO);

        PaymentResponse getPaymentById(Integer paymentId);

        List<PaymentResponse> getCustomerPayments(Integer customerId);

        PaymentResponse getBookingPayment(Integer bookingId);

        PaymentResponse updatePaymentStatus(Integer paymentId, PaymentStatus status);
}


