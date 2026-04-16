package com.gemini.BusTicketBookingSystem.controller;


import com.gemini.BusTicketBookingSystem.dto.request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.dto.response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import com.gemini.BusTicketBookingSystem.service.IPaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@Valid @RequestBody PaymentRequest requestDTO) {
        PaymentResponse response = paymentService.makePayment(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Integer paymentId) {
        PaymentResponse response = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers/{customerId}/payments")
    public ResponseEntity<List<PaymentResponse>> getCustomerPayments(@PathVariable Integer customerId) {
        List<PaymentResponse> payments = paymentService.getCustomerPayments(customerId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/bookings/{bookingId}/payment")
    public ResponseEntity<PaymentResponse> getBookingPayment(@PathVariable Integer bookingId) {
        PaymentResponse response = paymentService.getBookingPayment(bookingId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Integer paymentId,
            @RequestParam PaymentStatus status) {
        PaymentResponse response = paymentService.updatePaymentStatus(paymentId, status);
        return ResponseEntity.ok(response);
    }
}
