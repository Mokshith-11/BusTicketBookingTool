package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.dto.response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;

import java.util.List;
/*
 * - This service interface lists the Payment actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface IPaymentService {
        PaymentResponse makePayment(PaymentRequest requestDTO);

        PaymentResponse getPaymentById(Integer paymentId);

        List<PaymentResponse> getCustomerPayments(Integer customerId);

        PaymentResponse getBookingPayment(Integer bookingId);

        PaymentResponse updatePaymentStatus(Integer paymentId, PaymentStatus status);
}


