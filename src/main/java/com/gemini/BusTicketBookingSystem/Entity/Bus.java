package com.gemini.BusTicketBookingSystem.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "buses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer busId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "office_id", nullable = false)
//    private AgencyOffice office;

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
}