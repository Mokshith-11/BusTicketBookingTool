package com.gemini.BusTicketBookingSystem.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "agencies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
}