package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
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