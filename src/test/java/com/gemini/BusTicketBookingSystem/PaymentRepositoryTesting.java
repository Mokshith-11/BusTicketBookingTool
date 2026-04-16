package com.gemini.BusTicketBookingSystem;

import com.gemini.BusTicketBookingSystem.Dto.Request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.Entity.*;
import com.gemini.BusTicketBookingSystem.Repository.*;
import com.gemini.BusTicketBookingSystem.Service.Impl.PaymentServiceImpl;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
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

    private Payment payment;
    private Booking booking;
    private Customer customer;
    private PaymentRequest request;

    @BeforeEach
    void setUp() {
        Trip trip = new Trip();
        trip.setTripId(1);
        trip.setFare(new BigDecimal("500"));

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Vignesh");

        booking = new Booking();
        booking.setBookingId(1);
        booking.setTrip(trip);
        booking.setCustomer(customer);
        booking.setSeatNumber(5);
        booking.setStatus(BookingStatus.Booked);

        payment = new Payment();
        payment.setPaymentId(1);
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setAmount(new BigDecimal("500"));
        payment.setPaymentStatus(PaymentStatus.Success);

        request = new PaymentRequest();
        request.setBookingId(1);
        request.setCustomerId(1);
        request.setAmount(new BigDecimal("500"));
        request.setPaymentStatus(PaymentStatus.Success);
    }

    @Test
    void testMakePayment_Success() {
        when(bookingRepository.findById(1))
                .thenReturn(Optional.of(booking));

        when(customerRepository.findById(1))
                .thenReturn(Optional.of(customer));

        when(paymentRepository.existsPaymentByBookingId(1))
                .thenReturn(false);

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        PaymentResponse response = paymentService.makePayment(request);

        assertNotNull(response);
        assertEquals(1, response.getPaymentId());
        assertEquals(PaymentStatus.Success, response.getPaymentStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentById() {
        when(paymentRepository.findById(1))
                .thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.getPaymentById(1);

        assertNotNull(response);
        assertEquals(1, response.getPaymentId());
        assertEquals(new BigDecimal("500"), response.getAmount());
    }

    @Test
    void testGetCustomerPayments() {
        when(customerRepository.existsById(1))
                .thenReturn(true);

        when(paymentRepository.findPaymentsByCustomerId(1))
                .thenReturn(Arrays.asList(payment));

        var payments = paymentService.getCustomerPayments(1);

        assertEquals(1, payments.size());
        assertEquals("Vignesh", payments.get(0).getCustomerName());
    }

    @Test
    void testGetBookingPayment() {
        when(bookingRepository.existsById(1))
                .thenReturn(true);

        when(paymentRepository.findPaymentByBookingId(1))
                .thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.getBookingPayment(1);

        assertEquals(1, response.getBookingId());
        assertEquals(PaymentStatus.Success, response.getPaymentStatus());
    }

    @Test
    void testUpdatePaymentStatus() {
        when(paymentRepository.findById(1))
                .thenReturn(Optional.of(payment));

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        PaymentResponse response =
                paymentService.updatePaymentStatus(1, PaymentStatus.Success);

        assertEquals(PaymentStatus.Success, response.getPaymentStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
}