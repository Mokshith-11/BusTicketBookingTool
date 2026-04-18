package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Builder

public class AgencyResponse {
    private Integer agencyId;
    private String name;
    private String contactPersonName;
    private String email;
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
}

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

     public AgencyResponse() {
     }

     public AgencyResponse(Integer agencyId, String name, String contactPersonName, String email, String phone) {
         this.agencyId = agencyId;
         this.name = name;
         this.contactPersonName = contactPersonName;
         this.email = email;
         this.phone = phone;
     }
 }

