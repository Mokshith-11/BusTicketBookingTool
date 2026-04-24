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

/**
 * Service implementation for managing trip reviews.
 * Contains business logic for submitting reviews, retrieving reviews
 * by trip or customer, and deleting reviews.
 */
@Service
/*
 * - This class contains the real business logic for Review operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private ITripRepository tripRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    /**
     * Submits a new review for a completed trip.
     * Performs several validations before saving the review:
     * 1. Verifies the trip exists
     * 2. Verifies the customer exists
     * 3. Checks rating is between 1 and 5
     * 4. Ensures the trip has been completed (arrival time has passed)
     * 5. Prevents duplicate reviews (same customer cannot review same trip twice)
     * Records the current timestamp as the review date.
     * This method is transactional to ensure data consistency.
     *
     * @param tripId     - the ID of the trip being reviewed
     * @param requestDTO - contains customerId, rating (1-5), comment
     * @return ReviewResponse - the submitted review data
     */
    @Override
    @Transactional
    public ReviewResponse submitReview(Integer tripId, ReviewRequest requestDTO) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId",
                        requestDTO.getCustomerId()));

        // Validate that the rating is within the allowed range (1 to 5 stars)
        if (requestDTO.getRating() < 1 || requestDTO.getRating() > 5) {
            throw new InvalidOperationException("Submit Review",
                    "Rating must be between 1 and 5");
        }

        // Only allow reviews for trips that have already been completed
        if (trip.getArrivalTime().isAfter(LocalDateTime.now())) {
            throw new InvalidOperationException("Submit Review",
                    "Cannot review a trip that hasn't been completed yet");
        }

        // Prevent a customer from reviewing the same trip more than once
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

    /**
     * Retrieves all reviews for a specific trip.
     * First verifies the trip exists, then fetches all its reviews.
     *
     * @param tripId - the ID of the trip
     * @return List of ReviewResponse - all reviews for that trip
     */
    @Override
    public List<ReviewResponse> getTripReviews(Integer tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResourceNotFoundException("Trip", "tripId", tripId);
        }

        return reviewRepository.findByTripId(tripId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all reviews written by a specific customer.
     * First verifies the customer exists, then fetches all their reviews.
     *
     * @param customerId - the ID of the customer
     * @return List of ReviewResponse - all reviews by that customer
     */
    @Override
    public List<ReviewResponse> getCustomerReviews(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "customerId", customerId);
        }

        return reviewRepository.findByCustomerId(customerId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a review permanently from the database.
     * Finds the review by ID and removes it.
     * Throws ResourceNotFoundException if the review doesn't exist.
     * This method is transactional to ensure data consistency.
     *
     * @param reviewId - the ID of the review to delete
     */
    @Override
    @Transactional
    public void removeReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "reviewId", reviewId));


        reviewRepository.delete(review);
    }

    /**
     * Helper method to convert a Review entity to a ReviewResponse DTO.
     * Maps review details including trip info (from/to cities, date)
     * and customer info (ID, name).
     *
     * @param review - the Review entity to convert
     * @return ReviewResponse - the mapped DTO
     */
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
