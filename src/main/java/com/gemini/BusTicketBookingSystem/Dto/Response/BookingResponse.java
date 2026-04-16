package com.gemini.BusTicketBookingSystem.Dto.Response;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingResponse {
    private Integer bookingId;
    private Integer tripId;
    private Integer customerId;
    private String customerName;
    private Integer seatNumber;
    private String status;
}