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

/**
 * Service implementation for managing payments.
 * Contains business logic for processing payments, retrieving payment records,
 * and updating payment statuses. Each payment is linked to a booking and customer.
 */
@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    /**
     * Processes a payment for a booking.
     * Performs several validations before creating the payment:
     * 1. Verifies the booking exists
     * 2. Verifies the customer exists
     * 3. Checks the booking is in "Booked" status (not cancelled)
     * 4. Ensures no duplicate payment exists for the same booking
     * 5. Validates the payment amount matches the trip fare exactly
     * Sets the payment status to "Success" and records the current timestamp.
     * This method is transactional to ensure data consistency.
     *
     * @param requestDTO - contains bookingId, customerId, amount
     * @return PaymentResponse - the payment confirmation with all details
     */
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

    /**
     * Retrieves a single payment by its unique payment ID.
     * Throws ResourceNotFoundException if no payment exists with that ID.
     *
     * @param paymentId - the unique ID of the payment
     * @return PaymentResponse - the payment details
     */
    @Override
    public PaymentResponse getPaymentById(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "paymentId", paymentId));
        return convertToResponseDTO(payment);
    }

    /**
     * Retrieves all payments made by a specific customer.
     * First checks that the customer exists, then fetches all their payment records.
     *
     * @param customerId - the ID of the customer
     * @return List of PaymentResponse - all payments by that customer
     */
    @Override
    public List<PaymentResponse> getCustomerPayments(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "customerId", customerId);
        }

        return paymentRepository.findPaymentsByCustomerId(customerId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the payment linked to a specific booking.
     * First checks that the booking exists, then finds its associated payment.
     * Throws ResourceNotFoundException if the booking or payment is not found.
     *
     * @param bookingId - the ID of the booking
     * @return PaymentResponse - the payment details for that booking
     */
    @Override
    public PaymentResponse getBookingPayment(Integer bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking", "bookingId", bookingId);
        }

        Payment payment = paymentRepository.findFirstByBooking_BookingIdOrderByPaymentIdDesc(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "bookingId", bookingId));

        return convertToResponseDTO(payment);
    }

    /**
     * Updates the status of an existing payment.
     * Business rule: cannot change a "Success" payment to "Failed"
     * (once a payment succeeds, it cannot be marked as failed).
     * This method is transactional to ensure data consistency.
     *
     * @param paymentId - the ID of the payment to update
     * @param status    - the new PaymentStatus (Success, Failed, Pending)
     * @return PaymentResponse - the updated payment details
     */
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


    /**
     * Helper method to convert a Payment entity to a PaymentResponse DTO.
     * Maps payment details including booking info, customer info, trip ID, and seat number.
     *
     * @param payment - the Payment entity to convert
     * @return PaymentResponse - the mapped DTO
     */
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
