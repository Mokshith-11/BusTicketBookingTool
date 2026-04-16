package com.gemini.BusTicketBookingSystem.Service;

import com.gemini.BusTicketBookingSystem.Dto.Request.ReviewRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.ReviewResponse;

import java.util.List;

public interface IReviewService {
    ReviewResponse submitReview(Integer tripId, ReviewRequest requestDTO);
    List<ReviewResponse> getTripReviews(Integer tripId);
    List<ReviewResponse> getCustomerReviews(Integer customerId);
    void removeReview(Integer reviewId);
}
