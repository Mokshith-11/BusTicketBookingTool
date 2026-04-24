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

@RestController
@RequestMapping("/api/v1")
/*
 * - This controller is the API entry point for Review requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    // ✅ SUBMIT REVIEW
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
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

    // ✅ GET REVIEWS BY TRIP
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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

    // ✅ GET REVIEWS BY CUSTOMER
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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

    // ✅ DELETE REVIEW
    // DELETE disables or removes a resource by ID.
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
