package com.gemini.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "addresses")
@Builder
public class Addresses{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @NotBlank(message = "Address is required")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "City is required")
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank(message = "State is required")
    @Column(name = "state", nullable = false)
    private String state;

    @NotBlank(message = "Zip code is required")
    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Addresses() {
    }

    public Addresses(Integer addressId, String address, String city, String state, String zipCode) {
        this.addressId = addressId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}