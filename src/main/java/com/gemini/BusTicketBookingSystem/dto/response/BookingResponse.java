package com.gemini.BusTicketBookingSystem.dto.response;


import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Builder

 @Builder

public class BookingResponse {
    private Integer bookingId;
    private Integer tripId;
    private Integer customerId;
    private String customerName;
    private Integer seatNumber;
    private String status;


    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

     public Integer getBookingId() {
         return bookingId;
     }

     public void setBookingId(Integer bookingId) {
         this.bookingId = bookingId;
     }

     public Integer getTripId() {
         return tripId;
     }

     public void setTripId(Integer tripId) {
         this.tripId = tripId;
     }

     public Integer getCustomerId() {
         return customerId;
     }

     public void setCustomerId(Integer customerId) {
         this.customerId = customerId;
     }

     public String getCustomerName() {
         return customerName;
     }

     public void setCustomerName(String customerName) {
         this.customerName = customerName;
     }

     public Integer getSeatNumber() {
         return seatNumber;
     }

     public void setSeatNumber(Integer seatNumber) {
         this.seatNumber = seatNumber;
     }

     public String getStatus() {
         return status;
     }

     public void setStatus(String status) {
         this.status = status;
     }

     public BookingResponse() {
     }

     public BookingResponse(Integer bookingId, Integer tripId, Integer customerId, String customerName, Integer seatNumber, String status) {
         this.bookingId = bookingId;
         this.tripId = tripId;
         this.customerId = customerId;
         this.customerName = customerName;
         this.seatNumber = seatNumber;
         this.status = status;
     }
 }

