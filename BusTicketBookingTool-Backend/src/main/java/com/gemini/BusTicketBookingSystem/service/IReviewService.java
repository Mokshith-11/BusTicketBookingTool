package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.ReviewRequest;
import com.gemini.BusTicketBookingSystem.dto.response.ReviewResponse;

import java.util.List;

/**
 * Service interface for review management.
 * Defines the contract for submitting reviews, viewing reviews
 * by trip or customer, and deleting reviews.
 * Implemented by ReviewServiceImpl.
 */
public interface IReviewService {
    /** Submits a new review for a completed trip */
    ReviewResponse submitReview(Integer tripId, ReviewRequest requestDTO);

    /** Retrieves all reviews for a specific trip */
    List<ReviewResponse> getTripReviews(Integer tripId);

    /** Retrieves all reviews written by a specific customer */
    List<ReviewResponse> getCustomerReviews(Integer customerId);

    /** Deletes a review by its ID */
    void removeReview(Integer reviewId);
}
