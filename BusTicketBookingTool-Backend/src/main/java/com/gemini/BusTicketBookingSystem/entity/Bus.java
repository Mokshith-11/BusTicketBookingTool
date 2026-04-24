package com.gemini.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "buses")
 @Builder
/*
 * Beginner guide:
 * - This entity represents the Bus table/object stored in PostgreSQL.
 * - JPA annotations such as @Entity, @Id, @Column, @ManyToOne, and @OneToMany explain how Java fields map to database columns and relationships.
 * - Repositories save and read this entity; services convert it to DTOs before sending data back to the frontend.
 */
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer busId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private AgencyOffice office;

    @NotBlank(message = "Registration number is required")
    @Column(name = "registration_number", nullable = false, length = 20)
    private String registrationNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotBlank(message = "Bus type is required")
    @Column(name = "type", nullable = false, length = 30)
    private String type;


    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public AgencyOffice getOffice() {
        return office;
    }

    public void setOffice(AgencyOffice office) {
        this.office = office;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bus() {
    }

    public Bus(Integer busId, AgencyOffice office, String registrationNumber, Integer capacity, String type) {
        this.busId = busId;
        this.office = office;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.type = type;
    }
}