package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.Entity.Payment;
import com.gemini.BusTicketBookingSystem.Repository.IPaymentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final IPaymentRepository paymentRepository;

    public PaymentController(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public Payment processPayment(@RequestBody Payment payment) {
        payment.setPaymentStatus("SUCCESS");
        return paymentRepository.save(payment);
    }
}