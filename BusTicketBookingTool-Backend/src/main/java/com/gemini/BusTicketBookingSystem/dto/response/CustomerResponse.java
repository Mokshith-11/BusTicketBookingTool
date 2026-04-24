package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * Beginner guide:
 * - This response DTO is the safe shape of Customer data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class CustomerResponse {
    private Integer customerId;
    private String name;
    private String email;
    private String phone;
}