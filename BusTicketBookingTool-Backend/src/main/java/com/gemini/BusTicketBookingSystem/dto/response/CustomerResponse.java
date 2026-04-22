package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

/**
 * DTO for returning customer data to the client.
 * Contains the customer's basic information.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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