package com.gemini.BusTicketBookingSystem.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Builder



public class AgencyRequest {

    @NotBlank(message = "Agency name is required")
    private String name;

    @NotBlank(message = "Contact person name is required")
    private String contactPersonName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;


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

     public AgencyRequest() {
     }

     public AgencyRequest(String name, String contactPersonName, String email, String phone) {
         this.name = name;
         this.contactPersonName = contactPersonName;
         this.email = email;
         this.phone = phone;
     }
 }

