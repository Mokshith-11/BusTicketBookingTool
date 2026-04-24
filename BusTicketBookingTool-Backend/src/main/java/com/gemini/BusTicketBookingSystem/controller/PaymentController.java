package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.dto.response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import com.gemini.BusTicketBookingSystem.service.IPaymentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing payments.
 * Handles making payments for bookings, viewing payment details,
 * and updating payment statuses.
 * Base URL: /api/v1/payments
 */
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    /**
     * Processes a payment for a booking.
     * Validates that the booking exists, is in "Booked" status, hasn't been paid already,
     * and that the payment amount matches the trip fare.
     * Sets the payment status to "Success" and records the current timestamp.
     *
     * @param requestDTO - payment details: bookingId, customerId, amount (validated with @Valid)
     * @return ResponseEntity with HTTP 201 (Created) and the payment confirmation
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> makePayment(
            @Valid @RequestBody PaymentRequest requestDTO) {

        PaymentResponse response = paymentService.makePayment(requestDTO);

        ApiResponse<PaymentResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Payment successful",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Retrieves a single payment by its unique payment ID.
     * If the payment is not found, a ResourceNotFoundException is thrown.
     *
     * @param paymentId - the unique identifier of the payment to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and the payment details
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable Integer paymentId) {

        PaymentResponse response = paymentService.getPaymentById(paymentId);

        ApiResponse<PaymentResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Payment fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves all payments made by a specific customer.
     * Uses the customerId to look up all their payment records.
     * Throws ResourceNotFoundException if the customer doesn't exist.
     *
     * @param customerId - the ID of the customer whose payments to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and a list of payments
     */
    @GetMapping("/customers/{customerId}/payments")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getCustomerPayments(
            @PathVariable Integer customerId) {

        List<PaymentResponse> payments = paymentService.getCustomerPayments(customerId);

        ApiResponse<List<PaymentResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Customer payments fetched successfully",
                        payments);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the payment associated with a specific booking.
     * Each booking can have at most one payment. Throws ResourceNotFoundException
     * if the booking or its payment is not found.
     *
     * @param bookingId - the ID of the booking whose payment to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and the payment details
     */
    @GetMapping("/bookings/{bookingId}/payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> getBookingPayment(
            @PathVariable Integer bookingId) {

        PaymentResponse response = paymentService.getBookingPayment(bookingId);

        ApiResponse<PaymentResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Booking payment fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Updates the status of an existing payment (e.g., from Pending to Success or Failed).
     * Business rule: cannot change a "Success" payment to "Failed".
     * The new status is passed as a query parameter.
     *
     * @param paymentId - the ID of the payment to update
     * @param status    - the new payment status (Success, Failed, Pending)
     * @return ResponseEntity with HTTP 200 (OK) and the updated payment details
     */
    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePaymentStatus(
            @PathVariable Integer paymentId,
            @RequestParam String status) {

        PaymentResponse response = paymentService.updatePaymentStatus(paymentId, PaymentStatus.fromValue(status));

        ApiResponse<PaymentResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Payment status updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }
}
