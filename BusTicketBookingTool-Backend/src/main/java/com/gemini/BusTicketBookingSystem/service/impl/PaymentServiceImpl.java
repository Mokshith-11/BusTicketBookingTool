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
// PaymentServiceImpl manages payment creation, lookup, and status changes.
/*
 * - This class contains the real business logic for Payment operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    @Transactional
    // A payment is allowed only for a valid booked trip and for the exact trip fare amount.
    public PaymentResponse makePayment(PaymentRequest requestDTO) {
        Booking booking = bookingRepository.findById(requestDTO.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId",
                        requestDTO.getBookingId()));

        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId",
                        requestDTO.getCustomerId()));

        // Payments are only meaningful after a booking is successfully confirmed.
        if (booking.getStatus() != BookingStatus.Booked) {
            throw new InvalidOperationException("Make Payment",
                    "Cannot make payment for a booking that is not confirmed");
        }

        // This project allows only one payment record per booking.
        if (paymentRepository.existsPaymentByBookingId(requestDTO.getBookingId())) {
            throw new DuplicateResourceException("Payment", "bookingId",
                    requestDTO.getBookingId());
        }

        // The backend protects fare integrity even if the frontend sends a wrong amount.
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
    // Used when the payment details page asks for one payment record.
    public PaymentResponse getPaymentById(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "paymentId", paymentId));
        return convertToResponseDTO(payment);
    }

    @Override
    // Used for customer-level payment history screens.
    public List<PaymentResponse> getCustomerPayments(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "customerId", customerId);
        }

        return paymentRepository.findPaymentsByCustomerId(customerId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    // Returns the latest payment linked to a booking.
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
    // PATCH status changes are restricted by business rules, not only by enum values.
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


    // Converts the payment entity and its linked booking/customer data into API response format.
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


