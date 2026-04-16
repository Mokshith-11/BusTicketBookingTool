package com.gemini.BusTicketBookingSystem;

import com.gemini.BusTicketBookingSystem.entity.*;
import com.gemini.BusTicketBookingSystem.repository.IPaymentRepository;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentRepositoryTesting {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Test
    void testSavePayment_Positive() {
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("500"));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.Success);

        Payment saved = paymentRepository.save(payment);

        assertThat(saved).isNotNull();
        assertThat(saved.getPaymentId()).isNotNull();
    }

    @Test
    void testFindById_Positive() {
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("500"));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.Success);

        Payment saved = paymentRepository.save(payment);

        Optional<Payment> found =
                paymentRepository.findById(saved.getPaymentId());

        assertThat(found).isPresent();
    }

    @Test
    void testFindById_Negative_InvalidId() {
        Optional<Payment> found =
                paymentRepository.findById(-1);

        assertThat(found).isNotPresent();
    }

    @Test
    void testDeletePayment_Positive() {
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("500"));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.Success);

        Payment saved = paymentRepository.save(payment);

        paymentRepository.deleteById(saved.getPaymentId());

        Optional<Payment> deleted =
                paymentRepository.findById(saved.getPaymentId());

        assertThat(deleted).isNotPresent();
    }
}