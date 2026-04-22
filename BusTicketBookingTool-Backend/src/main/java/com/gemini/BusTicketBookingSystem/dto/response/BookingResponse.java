package com.gemini.BusTicketBookingSystem.dto.response;


import lombok.*;

/**
 * DTO for returning booking data to the client.
 * Contains the booking confirmation details including trip, customer, and seat info.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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