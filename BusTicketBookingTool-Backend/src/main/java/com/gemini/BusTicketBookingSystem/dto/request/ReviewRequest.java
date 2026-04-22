package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for submitting a review for a completed trip.
 * Contains the customer ID, a rating (1-5 stars), and a text comment.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    /** ID of the customer writing the review — required */
    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    /** Rating from 1 (worst) to 5 (best) — required */
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    /** Text feedback about the trip — required, max 500 characters */
    @NotBlank(message = "Comment cannot be empty")
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;

}