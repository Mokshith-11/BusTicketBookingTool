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

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    // ✅ MAKE PAYMENT
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

    // ✅ GET PAYMENT BY ID
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

    // ✅ GET CUSTOMER PAYMENTS
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

    // ✅ GET BOOKING PAYMENT
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

    // ✅ UPDATE PAYMENT STATUS
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
