package com.gemini.BusTicketBookingSystem.dto.response;
import lombok.*;

@Builder
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


    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public DriverResponse() {
    }

    public DriverResponse(Integer driverId, String licenseNumber, String name, String phone, Integer officeId) {
        this.driverId = driverId;
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.phone = phone;
        this.officeId = officeId;
    }
}
