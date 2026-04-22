package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for returning review data to the client.
 * Contains the review details including trip info and customer info.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    /** Unique review identifier */
    private Integer reviewId;

    /** ID of the trip being reviewed */
    private Integer tripId;

    /** ID of the customer who wrote the review */
    private Integer customerId;

    /** Name of the customer who wrote the review */
    private String customerName;

    /** Rating given (1-5 stars) */
    private Integer rating;

    /** Text comment/feedback */
    private String comment;

    /** Date the review was submitted */
    private LocalDateTime reviewDate;

    /** Departure city of the reviewed trip */
    private String fromCity;

    /** Destination city of the reviewed trip */
    private String toCity;

    /** Date of the reviewed trip */
    private LocalDateTime tripDate;

}