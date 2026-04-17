package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.ReviewRequest;
import com.gemini.BusTicketBookingSystem.dto.response.ReviewResponse;
import com.gemini.BusTicketBookingSystem.service.IReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @PostMapping("/trips/{tripId}/reviews")
    public ResponseEntity<ReviewResponse> submitReview(
            @PathVariable Integer tripId,
            @Valid @RequestBody ReviewRequest requestDTO) {
        ReviewResponse response = reviewService.submitReview(tripId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/trips/{tripId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getTripReviews(@PathVariable Integer tripId) {
        List<ReviewResponse> reviews = reviewService.getTripReviews(tripId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/customers/{customerId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getCustomerReviews(@PathVariable Integer customerId) {
        List<ReviewResponse> reviews = reviewService.getCustomerReviews(customerId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> removeReview(@PathVariable Integer reviewId) {
        reviewService.removeReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
 