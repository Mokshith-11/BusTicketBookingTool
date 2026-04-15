package com.gemini.BusTicketBookingSystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "agency_offices")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgencyOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id")
    private Integer officeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @Column(name = "office_mail", length = 100)
    private String officeMail;

    @Column(name = "office_contact_person_name", length = 50)
    private String officeContactPersonName;

    @Column(name = "office_contact_number", length = 10)
    private String officeContactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_address_id")
    private Addresses officeAddress;
}