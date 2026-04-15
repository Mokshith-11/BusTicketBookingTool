package com.gemini.BusTicketBookingSystem.Entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank
    @Column(name = "state", nullable = false)
    private String state;

    @NotBlank
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<AgencyOffice> agencyOffices;
}
