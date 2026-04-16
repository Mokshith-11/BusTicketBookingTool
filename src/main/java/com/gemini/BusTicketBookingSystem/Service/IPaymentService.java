package com.gemini.BusTicketBookingSystem.Service;

import com.gemini.BusTicketBookingSystem.Dto.Request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;

import java.util.List;

public interface IPaymentService {
        PaymentResponse makePayment(PaymentRequest requestDTO);

        PaymentResponse getPaymentById(Integer paymentId);

        List<PaymentResponse> getCustomerPayments(Integer customerId);

        PaymentResponse getBookingPayment(Integer bookingId);

        PaymentResponse updatePaymentStatus(Integer paymentId, PaymentStatus status);
}


