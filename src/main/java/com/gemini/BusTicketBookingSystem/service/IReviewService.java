package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.dto.request.ReviewRequest;
import com.gemini.BusTicketBookingSystem.dto.response.ReviewResponse;

import java.util.List;

public interface IReviewService {
    ReviewResponse submitReview(Integer tripId, ReviewRequest requestDTO);
    List<ReviewResponse> getTripReviews(Integer tripId);
    List<ReviewResponse> getCustomerReviews(Integer customerId);
    void removeReview(Integer reviewId);
}
