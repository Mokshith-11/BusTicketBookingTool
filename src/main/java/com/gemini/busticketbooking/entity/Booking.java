package com.gemini.busticketbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long tripId;

    private Long customerId;

    private Integer seatNumber;

    private String bookingStatus;
}