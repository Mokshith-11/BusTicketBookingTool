package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.Customer;
import com.gemini.BusTicketBookingSystem.entity.Review;
import com.gemini.BusTicketBookingSystem.entity.Trip;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.InvalidOperationException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.ICustomerRepository;
import com.gemini.BusTicketBookingSystem.repository.IReviewRepository;
import com.gemini.BusTicketBookingSystem.repository.ITripRepository;
import com.gemini.BusTicketBookingSystem.service.IReviewService;
import com.gemini.BusTicketBookingSystem.dto.request.ReviewRequest;
import com.gemini.BusTicketBookingSystem.dto.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private ITripRepository tripRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    @Transactional
    public ReviewResponse submitReview(Integer tripId, ReviewRequest requestDTO) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId",
                        requestDTO.getCustomerId()));

        // Validate rating is between 1 and 5
        if (requestDTO.getRating() < 1 || requestDTO.getRating() > 5) {
            throw new InvalidOperationException("Submit Review",
                    "Rating must be between 1 and 5");
        }

        // Check if trip has already departed (can only review completed trips)
        if (trip.getArrivalTime().isAfter(LocalDateTime.now())) {
            throw new InvalidOperationException("Submit Review",
                    "Cannot review a trip that hasn't been completed yet");
        }

        // Check if customer has already reviewed this trip
        if (reviewRepository. existsByTripAndCustomer(tripId,
                requestDTO.getCustomerId())) {
            throw new DuplicateResourceException("Review",
                    "tripId-customerId",
                    tripId + "-" + requestDTO.getCustomerId());
        }

        Review review = new Review();
        review.setTrip(trip);
        review.setCustomer(customer);
        review.setRating(requestDTO.getRating());
        review.setComment(requestDTO.getComment());
        review.setReviewDate(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        return convertToResponseDTO(savedReview);
    }

    @Override
    public List<ReviewResponse> getTripReviews(Integer tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResourceNotFoundException("Trip", "tripId", tripId);
        }

        return reviewRepository.findByCustomerId(tripId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getCustomerReviews(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "customerId", customerId);
        }

        return reviewRepository.findByCustomerId(customerId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "reviewId", reviewId));


        reviewRepository.delete(review);
    }

    private ReviewResponse convertToResponseDTO(Review review) {
        ReviewResponse dto = new ReviewResponse();
        dto.setReviewId(review.getReviewId());
        dto.setTripId(review.getTrip().getTripId());
        dto.setCustomerId(review.getCustomer().getCustomerId());
        dto.setCustomerName(review.getCustomer().getName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReviewDate(review.getReviewDate());
        dto.setFromCity(review.getTrip().getRoute().getFromCity());
        dto.setToCity(review.getTrip().getRoute().getToCity());
        dto.setTripDate(review.getTrip().getTripDate());
        return dto;
    }
}
