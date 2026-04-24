package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

/**
 * DTO for returning customer data to the client.
 * Contains the customer's basic information.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This response DTO is the safe shape of Customer data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class CustomerResponse {
    /** Unique customer identifier */
    private Integer customerId;

    /** Full name of the customer */
    private String name;

    /** Email address */
    private String email;

    /** Phone number */
    private String phone;
}