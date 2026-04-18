package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;


 @NoArgsConstructor @AllArgsConstructor @Builder

public class AgencyOfficeRequest {

    @NotNull(message = "Agency ID is required")
    private Integer agencyId;

    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;

    @NotNull(message = "Office address ID is required")
    private Integer officeAddressId;

     public Integer getAgencyId() {
         return agencyId;
     }

     public void setAgencyId(Integer agencyId) {
         this.agencyId = agencyId;
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

     public Integer getOfficeAddressId() {
         return officeAddressId;
     }

     public void setOfficeAddressId(Integer officeAddressId) {
         this.officeAddressId = officeAddressId;
     }


     public AgencyOfficeRequest() {
     }

     public AgencyOfficeRequest(Integer agencyId, String officeMail, String officeContactPersonName, String officeContactNumber, Integer officeAddressId) {
         this.agencyId = agencyId;
         this.officeMail = officeMail;
         this.officeContactPersonName = officeContactPersonName;
         this.officeContactNumber = officeContactNumber;
         this.officeAddressId = officeAddressId;
     }

 }
