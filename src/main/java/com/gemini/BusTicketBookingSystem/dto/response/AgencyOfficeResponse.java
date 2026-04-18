package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Builder


public class AgencyOfficeResponse {
    private Integer officeId;
    private Integer agencyId;
    private String agencyName;
    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;
    private AddressResponse officeAddress;


    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
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

    public AddressResponse getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(AddressResponse officeAddress) {
        this.officeAddress = officeAddress;
    }
}
     public Integer getOfficeId() {
         return officeId;
     }

     public void setOfficeId(Integer officeId) {
         this.officeId = officeId;
     }

     public Integer getAgencyId() {
         return agencyId;
     }

     public void setAgencyId(Integer agencyId) {
         this.agencyId = agencyId;
     }

     public String getAgencyName() {
         return agencyName;
     }

     public void setAgencyName(String agencyName) {
         this.agencyName = agencyName;
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

     public AddressResponse getOfficeAddress() {
         return officeAddress;
     }

     public void setOfficeAddress(AddressResponse officeAddress) {
         this.officeAddress = officeAddress;
     }

     public AgencyOfficeResponse() {
     }

     public AgencyOfficeResponse(Integer officeId, Integer agencyId, String agencyName, String officeMail, String officeContactPersonName, String officeContactNumber, AddressResponse officeAddress) {
         this.officeId = officeId;
         this.agencyId = agencyId;
         this.agencyName = agencyName;
         this.officeMail = officeMail;
         this.officeContactPersonName = officeContactPersonName;
         this.officeContactNumber = officeContactNumber;
         this.officeAddress = officeAddress;
     }
 }

