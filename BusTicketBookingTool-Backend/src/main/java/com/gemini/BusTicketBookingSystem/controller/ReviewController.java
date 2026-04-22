package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.ReviewRequest;
import com.gemini.BusTicketBookingSystem.dto.response.ReviewResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IReviewService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing trip reviews.
 * Allows customers to submit reviews for completed trips,
 * view reviews by trip or customer, and delete reviews.
 * Base URL: /api/v1
 */
@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    /**
     * Submits a new review for a specific trip.
     * Validates that the trip is completed (arrival time has passed),
     * the rating is between 1-5, and the customer hasn't already reviewed this trip.
     *
     * @param tripId     - the ID of the trip being reviewed
     * @param requestDTO - review details: customerId, rating (1-5), comment
     * @return ResponseEntity with HTTP 201 (Created) and the submitted review data
     */
    @PostMapping("/trips/{tripId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> submitReview(
            @PathVariable Integer tripId,
            @Valid @RequestBody ReviewRequest requestDTO) {

        ReviewResponse response = reviewService.submitReview(tripId, requestDTO);

        ApiResponse<ReviewResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Review submitted successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Retrieves all reviews for a specific trip.
     * Returns a list of all reviews (ratings and comments) left by customers
     * for the given trip. Throws ResourceNotFoundException if the trip doesn't exist.
     *
     * @param tripId - the ID of the trip whose reviews to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and a list of reviews
     */
    @GetMapping("/trips/{tripId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getTripReviews(
            @PathVariable Integer tripId) {

        List<ReviewResponse> reviews = reviewService.getTripReviews(tripId);

        ApiResponse<List<ReviewResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Trip reviews fetched successfully",
                        reviews);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves all reviews written by a specific customer.
     * Returns a list of all reviews that a customer has submitted across all trips.
     * Throws ResourceNotFoundException if the customer doesn't exist.
     *
     * @param customerId - the ID of the customer whose reviews to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and a list of the customer's reviews
     */
    @GetMapping("/customers/{customerId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getCustomerReviews(
            @PathVariable Integer customerId) {

        List<ReviewResponse> reviews = reviewService.getCustomerReviews(customerId);

        ApiResponse<List<ReviewResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Customer reviews fetched successfully",
                        reviews);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Deletes a review by its unique review ID.
     * Permanently removes the review from the system.
     * Throws ResourceNotFoundException if the review doesn't exist.
     *
     * @param reviewId - the unique ID of the review to delete
     * @return ResponseEntity with HTTP 200 (OK) and a success message
     */
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<String>> removeReview(
            @PathVariable Integer reviewId) {

        reviewService.removeReview(reviewId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Review removed successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }
}
