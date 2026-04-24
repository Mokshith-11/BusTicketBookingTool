package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.ReviewRequest;
import com.gemini.BusTicketBookingSystem.dto.response.ReviewResponse;

import java.util.List;
/*
 * - This service interface lists the Review actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface IReviewService {
    ReviewResponse submitReview(Integer tripId, ReviewRequest requestDTO);
    List<ReviewResponse> getTripReviews(Integer tripId);
    List<ReviewResponse> getCustomerReviews(Integer customerId);
    void removeReview(Integer reviewId);
}
