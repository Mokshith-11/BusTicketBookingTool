package com.sprint.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

//    // Many reviews by one customer
//    @NotNull(message = "Customer is required")
//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    // Many reviews for one trip
    @NotNull(message = "Trip is required")
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    // Rating must be between 1 and 5
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating minimum is 1")
    @Max(value = 5, message = "Rating maximum is 5")
    private Integer rating;

    // @NotBlank means comment cannot be empty string
    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    private LocalDateTime reviewDate;
}