package com.gemini.BusTicketBookingSystem.Dto.Response;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DriverResponse {

    private Integer driverId;
    private String licenseNumber;
    private String name;
    private String phone;
    private Integer officeId;

    public void setOfficeName(String officeMail) {
    }

    public void setAddressId(Integer addressId) {
    }

    public void setAddress(String s) {
    }
//    private AddressResponse address;
}
