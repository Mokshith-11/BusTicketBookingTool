package com.capgemini.busticketbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long bookingId;
    private Double amount;
    private String paymentMode;
    private String paymentStatus;
}