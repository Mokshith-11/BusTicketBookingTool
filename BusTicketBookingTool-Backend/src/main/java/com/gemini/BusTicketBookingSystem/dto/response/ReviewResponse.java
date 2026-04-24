package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
/*
 * Beginner guide:
 * - This response DTO is the safe shape of Review data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class ReviewResponse {

    private Integer reviewId;

    private Integer tripId;
    private Integer customerId;
    private String customerName;

    private Integer rating;
    private String comment;

    private LocalDateTime reviewDate;

    private String fromCity;
    private String toCity;
    private LocalDateTime tripDate;

}