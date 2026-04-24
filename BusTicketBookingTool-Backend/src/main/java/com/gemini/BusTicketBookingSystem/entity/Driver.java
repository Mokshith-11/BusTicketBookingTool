package com.gemini.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "drivers")
@Builder
/*
 * - This entity represents the Driver table/object stored in PostgreSQL.
 * - JPA annotations such as @Entity, @Id, @Column, @ManyToOne, and @OneToMany explain how Java fields map to database columns and relationships.
 * - Repositories save and read this entity; services convert it to DTOs before sending data back to the frontend.
 */
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Integer driverId;

    @NotBlank(message = "License number is required")
    @Column(name = "license_number", nullable = false, length = 20)
    private String licenseNumber;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Phone is required")
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private AgencyOffice office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Addresses address;

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

    public AgencyOffice getOffice() {
        return office;
    }

    public void setOffice(AgencyOffice office) {
        this.office = office;
    }

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    public Driver() {
    }

    public Driver(Integer driverId, String licenseNumber, String name, String phone, AgencyOffice office, Addresses address) {
        this.driverId = driverId;
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.phone = phone;
        this.office = office;
        this.address = address;
    }
}