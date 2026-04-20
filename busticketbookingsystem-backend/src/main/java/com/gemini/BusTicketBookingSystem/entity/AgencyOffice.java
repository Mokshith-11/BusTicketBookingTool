package com.gemini.BusTicketBookingSystem.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agency_offices")
 @Builder
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

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public String getOfficeMail() {
        return officeMail;
    }

    public void setOfficeMail(String officeMail) {
        this.officeMail = officeMail;
    }

    public String getOfficeContactPersonName() {
        return officeContactPersonName;
    }

    public void setOfficeContactPersonName(String officeContactPersonName) {
        this.officeContactPersonName = officeContactPersonName;
    }

    public String getOfficeContactNumber() {
        return officeContactNumber;
    }

    public void setOfficeContactNumber(String officeContactNumber) {
        this.officeContactNumber = officeContactNumber;
    }

    public Addresses getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(Addresses officeAddress) {
        this.officeAddress = officeAddress;
    }

    public AgencyOffice() {
    }

    public AgencyOffice(Integer officeId, Agency agency, String officeMail, String officeContactPersonName, String officeContactNumber, Addresses officeAddress) {
        this.officeId = officeId;
        this.agency = agency;
        this.officeMail = officeMail;
        this.officeContactPersonName = officeContactPersonName;
        this.officeContactNumber = officeContactNumber;
        this.officeAddress = officeAddress;
    }
}