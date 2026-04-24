package com.gemini.BusTicketBookingSystem.dto.response;


import lombok.*;

/**
 * DTO for returning booking data to the client.
 * Contains the booking confirmation details including trip, customer, and seat info.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This response DTO is the safe shape of Booking data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class BookingResponse {
    /** Unique booking identifier */
    private Integer bookingId;

    /** ID of the trip this booking is for */
    private Integer tripId;

    /** ID of the customer who made the booking */
    private Integer customerId;

    /** Name of the customer who made the booking */
    private String customerName;

    /** The booked seat number */
    private Integer seatNumber;

    /** Current booking status ("Booked" or "Available") */
    private String status;
}