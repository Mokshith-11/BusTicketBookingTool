package com.sprint.busticketbooking.controller;

import com.sprint.busticketbooking.entity.Payment;
import com.sprint.busticketbooking.repository.IPaymentRepository;
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