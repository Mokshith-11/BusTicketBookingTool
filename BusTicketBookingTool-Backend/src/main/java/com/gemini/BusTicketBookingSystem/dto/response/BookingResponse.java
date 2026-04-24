package com.gemini.BusTicketBookingSystem.dto.response;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This response DTO is the safe shape of Booking data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class BookingResponse {
    private Integer bookingId;
    private Integer tripId;
    private Integer customerId;
    private String customerName;
    private Integer seatNumber;
    private String status;
}