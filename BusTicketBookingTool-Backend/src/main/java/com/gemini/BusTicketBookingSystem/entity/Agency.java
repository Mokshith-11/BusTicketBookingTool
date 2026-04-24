package com.gemini.BusTicketBookingSystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "agencies")
 @Builder
/*
 * - This entity represents the Agency table/object stored in PostgreSQL.
 * - JPA annotations such as @Entity, @Id, @Column, @ManyToOne, and @OneToMany explain how Java fields map to database columns and relationships.
 * - Repositories save and read this entity; services convert it to DTOs before sending data back to the frontend.
 */
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_id")
    private Integer agencyId;

    @NotBlank(message = "Agency name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Contact person name is required")
    @Column(name = "contact_person_name", nullable = false, length = 30)
    private String contactPersonName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Phone is required")
    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Agency() {
    }

    public Agency(Integer agencyId, String name, String contactPersonName, String email, String phone) {
        this.agencyId = agencyId;
        this.name = name;
        this.contactPersonName = contactPersonName;
        this.email = email;
        this.phone = phone;
    }
}