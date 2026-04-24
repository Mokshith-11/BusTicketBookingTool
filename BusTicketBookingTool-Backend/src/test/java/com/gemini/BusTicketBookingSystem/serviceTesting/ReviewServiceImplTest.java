package com.gemini.BusTicketBookingSystem.serviceTesting;



import com.gemini.BusTicketBookingSystem.dto.request.ReviewRequest;
import com.gemini.BusTicketBookingSystem.dto.response.ReviewResponse;
import com.gemini.BusTicketBookingSystem.entity.*;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.InvalidOperationException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.repository.IReviewRepository;
import com.gemini.BusTicketBookingSystem.repository.ITripRepository;
import com.gemini.BusTicketBookingSystem.service.impl.ReviewServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private IReviewRepository reviewRepository;

    @Mock
    private ITripRepository tripRepository;

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Trip trip;
    private Customer customer;
    private Review review;
    private ReviewRequest request;

    @BeforeEach
    void setUp() {

        trip = new Trip();
        trip.setTripId(1);
        trip.setArrivalTime(LocalDateTime.now().minusHours(2)); // completed trip

        Route route = new Route();
        route.setFromCity("Chennai");
        route.setToCity("Bangalore");
        trip.setRoute(route);
        trip.setTripDate(LocalDateTime.now().minusDays(1));

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Aksha");

        review = new Review();
        review.setReviewId(1);
        review.setTrip(trip);
        review.setCustomer(customer);
        review.setRating(5);
        review.setComment("Good trip");
        review.setReviewDate(LocalDateTime.now());

        request = new ReviewRequest();
        request.setCustomerId(1);
        request.setRating(5);
        request.setComment("Good trip");
    }

    // SUCCESS
    @Test
    void testSubmitReview_Success() {

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(reviewRepository.existsByTripAndCustomer(1,1)).thenReturn(false);
        when(reviewRepository.save(any())).thenReturn(review);

        ReviewResponse response = reviewService.submitReview(1, request);

        assertNotNull(response);
        assertEquals(5, response.getRating());

        verify(reviewRepository).save(any());
    }

    // ❌ TRIP NOT FOUND
    @Test
    void testSubmitReview_TripNotFound() {

        when(tripRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.submitReview(1, request));
    }

    // ❌ CUSTOMER NOT FOUND
    @Test
    void testSubmitReview_CustomerNotFound() {

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.submitReview(1, request));
    }

    // ❌ INVALID RATING
    @Test
    void testSubmitReview_InvalidRating() {

        request.setRating(6);

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        assertThrows(InvalidOperationException.class,
                () -> reviewService.submitReview(1, request));
    }

    // ❌ TRIP NOT COMPLETED
    @Test
    void testSubmitReview_TripNotCompleted() {

        trip.setArrivalTime(LocalDateTime.now().plusHours(2));

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        assertThrows(InvalidOperationException.class,
                () -> reviewService.submitReview(1, request));
    }

    // ❌ DUPLICATE REVIEW
    @Test
    void testSubmitReview_Duplicate() {

        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(reviewRepository.existsByTripAndCustomer(1,1)).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> reviewService.submitReview(1, request));
    }

    // GET TRIP REVIEWS
    @Test
    void testGetTripReviews_Success() {

        when(tripRepository.existsById(1)).thenReturn(true);
        when(reviewRepository.findByTripId(1)).thenReturn(List.of(review));

        List<ReviewResponse> result = reviewService.getTripReviews(1);

        assertEquals(1, result.size());
    }

    // ❌ TRIP NOT FOUND
    @Test
    void testGetTripReviews_NotFound() {

        when(tripRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.getTripReviews(1));
    }

    // GET CUSTOMER REVIEWS
    @Test
    void testGetCustomerReviews_Success() {

        when(customerRepository.existsById(1)).thenReturn(true);
        when(reviewRepository.findByCustomerId(1)).thenReturn(List.of(review));

        List<ReviewResponse> result = reviewService.getCustomerReviews(1);

        assertEquals(1, result.size());
    }

    // ❌ CUSTOMER NOT FOUND
    @Test
    void testGetCustomerReviews_NotFound() {

        when(customerRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.getCustomerReviews(1));
    }

    // DELETE REVIEW
    @Test
    void testRemoveReview_Success() {

        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        reviewService.removeReview(1);

        verify(reviewRepository).delete(review);
    }

    // ❌ DELETE NOT FOUND
    @Test
    void testRemoveReview_NotFound() {

        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.removeReview(1));
    }
}