package com.gemini.BusTicketBookingSystem.dto.response;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DriverResponse {

    private Integer driverId;
    private String licenseNumber;
    private String name;
    private String phone;
    private Integer officeId;
    private String officeName;
    private Integer addressId;
    private String address;

//    private AddressResponse address;
}
