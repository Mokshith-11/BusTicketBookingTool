package com.gemini.BusTicketBookingSystem.dto.response;
import lombok.*;

/**
 * DTO for returning driver data to the client.
 * Contains driver details including office assignment and address.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
/*
 * - This response DTO is the safe shape of Driver data returned to the frontend.
 * - Services convert database entities into this class so API responses do not expose unwanted internal fields.
 * - Controllers usually place this object inside ApiResponse with status code, message, and data.
 */
public class DriverResponse {

    /** Unique driver identifier */
    private Integer driverId;

    /** Driving license number */
    private String licenseNumber;

    /** Full name of the driver */
    private String name;

    /** Phone number */
    private String phone;

    /** ID of the office this driver is assigned to */
    private Integer officeId;

    /** Name/email of the office (used as office identifier) */
    private String officeName;

    /** ID of the driver's residential address */
    private Integer addressId;

    /** Formatted address string (street, city, state) */
    private String address;

//    private AddressResponse address;
}
