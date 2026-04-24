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

/**
 * Service interface for payment management.
 * Defines the contract for processing payments, viewing payment records,
 * and updating payment statuses.
 * Implemented by PaymentServiceImpl.
 */
public interface IPaymentService {
        /** Processes a payment for a booking */
        PaymentResponse makePayment(PaymentRequest requestDTO);

        /** Retrieves a payment by its unique ID */
        PaymentResponse getPaymentById(Integer paymentId);

        /** Retrieves all payments made by a specific customer */
        List<PaymentResponse> getCustomerPayments(Integer customerId);

        /** Retrieves the payment linked to a specific booking */
        PaymentResponse getBookingPayment(Integer bookingId);

        /** Updates the status of a payment (e.g., Success, Failed) */
        PaymentResponse updatePaymentStatus(Integer paymentId, PaymentStatus status);
}
