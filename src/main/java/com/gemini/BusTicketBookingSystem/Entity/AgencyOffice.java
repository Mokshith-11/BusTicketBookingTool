package com.gemini.BusTicketBookingSystem.Entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agency_offices")
public class AgencyOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id")
    private Integer officeId;

    @NotBlank
    @Column(name = "office_mail")
    private String officeMail;

    @NotBlank
    @Column(name = "office_contact_person_name")
    private String officeContactPersonName;

    @NotBlank
    @Column(name = "office_contact_number")
    private String officeContactNumber;

    // Many offices belong to one agency
    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    // Many offices belong to one address
    @ManyToOne
    @JoinColumn(name = "office_address_id")
    private Addresses address;
}

