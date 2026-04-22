package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.Booking;
import com.gemini.BusTicketBookingSystem.entity.Payment;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.InvalidOperationException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IBookingRepository;
import com.gemini.BusTicketBookingSystem.repository.IPaymentRepository;
import com.gemini.BusTicketBookingSystem.service.IPaymentService;
import com.gemini.BusTicketBookingSystem.dto.request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.dto.response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import com.gemini.BusTicketBookingSystem.repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    @Transactional
    public PaymentResponse makePayment(PaymentRequest requestDTO) {
        Booking booking = bookingRepository.findById(requestDTO.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId",
                        requestDTO.getBookingId()));

        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId",
                        requestDTO.getCustomerId()));

        if (booking.getStatus() != BookingStatus.Booked) {
            throw new InvalidOperationException("Make Payment",
                    "Cannot make payment for a booking that is not confirmed");
        }

        if (paymentRepository.existsPaymentByBookingId(requestDTO.getBookingId())) {
            throw new DuplicateResourceException("Payment", "bookingId",
                    requestDTO.getBookingId());
        }

        if (requestDTO.getAmount().compareTo(booking.getTrip().getFare()) != 0) {
            throw new InvalidOperationException("Make Payment",
                    "Payment amount must match trip fare of " + booking.getTrip().getFare());
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setAmount(requestDTO.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.Success);
        Payment savedPayment = paymentRepository.save(payment);
        return convertToResponseDTO(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "paymentId", paymentId));
        return convertToResponseDTO(payment);
    }

    @Override
    public List<PaymentResponse> getCustomerPayments(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "customerId", customerId);
        }

        return paymentRepository.findPaymentsByCustomerId(customerId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse getBookingPayment(Integer bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking", "bookingId", bookingId);
        }

        Payment payment = paymentRepository.findFirstByBooking_BookingIdOrderByPaymentIdDesc(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "bookingId", bookingId));

        return convertToResponseDTO(payment);
    }

    @Override
    @Transactional
    public PaymentResponse updatePaymentStatus(Integer paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "paymentId", paymentId));

        if (payment.getPaymentStatus() == PaymentStatus.Success && status == PaymentStatus.Failed) {
            throw new InvalidOperationException("Update Payment Status",
                    "Cannot change status from Success to Failed");
        }

        payment.setPaymentStatus(status);
        Payment updatedPayment = paymentRepository.save(payment);

        return convertToResponseDTO(updatedPayment);
    }


    private PaymentResponse convertToResponseDTO(Payment payment) {
        PaymentResponse dto = new PaymentResponse();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBooking().getBookingId());
        dto.setCustomerId(payment.getCustomer().getCustomerId());
        dto.setCustomerName(payment.getCustomer().getName());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setTripId(payment.getBooking().getTrip().getTripId());
        dto.setSeatNumber(payment.getBooking().getSeatNumber());
        return dto;
    }
}


