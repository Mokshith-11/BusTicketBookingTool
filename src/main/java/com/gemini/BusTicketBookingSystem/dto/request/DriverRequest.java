package com.gemini.BusTicketBookingSystem.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder

public class DriverRequest {

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Office ID is required")
    private Integer officeId;

    @NotNull(message = "Address ID is required")
    private Integer addressId;

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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public DriverRequest() {
    }

    public DriverRequest(String licenseNumber, String name, String phone, Integer officeId, Integer addressId) {
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.phone = phone;
        this.officeId = officeId;
        this.addressId = addressId;
    }

}