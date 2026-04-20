package com.gemini.BusTicketBookingSystem.serviceTesting;


import com.gemini.BusTicketBookingSystem.dto.request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.dto.response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.entity.*;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.InvalidOperationException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IBookingRepository;
import com.gemini.BusTicketBookingSystem.repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.repository.IPaymentRepository;
import com.gemini.BusTicketBookingSystem.service.impl.PaymentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private IPaymentRepository paymentRepository;

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Booking booking;
    private Customer customer;
    private Payment payment;
    private PaymentRequest request;
    private Trip trip;

    @BeforeEach
    void setUp() {

        trip = new Trip();
        trip.setTripId(1);
        trip.setFare(BigDecimal.valueOf(500));

        booking = new Booking();
        booking.setBookingId(1);
        booking.setTrip(trip);
        booking.setSeatNumber(5);
        booking.setStatus(BookingStatus.Booked);

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("John");

        payment = new Payment();
        payment.setPaymentId(1);
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setAmount(BigDecimal.valueOf(500));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.Success);

        request = new PaymentRequest();
        request.setBookingId(1);
        request.setCustomerId(1);
        request.setAmount(BigDecimal.valueOf(500));
    }

    // ✅ MAKE PAYMENT SUCCESS
    @Test
    void testMakePayment_Success() {

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(paymentRepository.existsPaymentByBookingId(1)).thenReturn(false);
        when(paymentRepository.save(any())).thenReturn(payment);

        PaymentResponse response = paymentService.makePayment(request);

        assertNotNull(response);
        assertEquals(1, response.getBookingId());

        verify(paymentRepository).save(any());
    }

    // ❌ BOOKING NOT FOUND
    @Test
    void testMakePayment_BookingNotFound() {

        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.makePayment(request));
    }

    // ❌ CUSTOMER NOT FOUND
    @Test
    void testMakePayment_CustomerNotFound() {

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.makePayment(request));
    }

    // ❌ INVALID BOOKING STATUS
    @Test
    void testMakePayment_InvalidStatus() {

        booking.setStatus(BookingStatus.Available);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        assertThrows(InvalidOperationException.class,
                () -> paymentService.makePayment(request));
    }

    // ❌ DUPLICATE PAYMENT
    @Test
    void testMakePayment_Duplicate() {

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(paymentRepository.existsPaymentByBookingId(1)).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> paymentService.makePayment(request));
    }

    // ❌ WRONG AMOUNT
    @Test
    void testMakePayment_InvalidAmount() {

        request.setAmount(BigDecimal.valueOf(100));

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(paymentRepository.existsPaymentByBookingId(1)).thenReturn(false);

        assertThrows(InvalidOperationException.class,
                () -> paymentService.makePayment(request));
    }

    // ✅ GET PAYMENT BY ID
    @Test
    void testGetPaymentById_Success() {

        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.getPaymentById(1);

        assertEquals(1, response.getPaymentId());
    }

    // ❌ PAYMENT NOT FOUND
    @Test
    void testGetPaymentById_NotFound() {

        when(paymentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.getPaymentById(1));
    }

    // ✅ GET CUSTOMER PAYMENTS
    @Test
    void testGetCustomerPayments_Success() {

        when(customerRepository.existsById(1)).thenReturn(true);
        when(paymentRepository.findPaymentsByCustomerId(1))
                .thenReturn(List.of(payment));

        List<PaymentResponse> result = paymentService.getCustomerPayments(1);

        assertEquals(1, result.size());
    }

    // ❌ CUSTOMER NOT FOUND
    @Test
    void testGetCustomerPayments_NotFound() {

        when(customerRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.getCustomerPayments(1));
    }

    // ✅ GET BOOKING PAYMENT
    @Test
    void testGetBookingPayment_Success() {

        when(bookingRepository.existsById(1)).thenReturn(true);
        when(paymentRepository.findPaymentByBookingId(1))
                .thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.getBookingPayment(1);

        assertEquals(1, response.getBookingId());
    }

    // ❌ BOOKING NOT FOUND
    @Test
    void testGetBookingPayment_BookingNotFound() {

        when(bookingRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.getBookingPayment(1));
    }


    // ❌ INVALID STATUS CHANGE
    @Test
    void testUpdatePaymentStatus_Invalid() {

        payment.setPaymentStatus(PaymentStatus.Success);

        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        assertThrows(InvalidOperationException.class,
                () -> paymentService.updatePaymentStatus(1, PaymentStatus.Failed));
    }
}