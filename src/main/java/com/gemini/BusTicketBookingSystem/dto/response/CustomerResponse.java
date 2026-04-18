package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Builder

public class CustomerResponse {
    private Integer customerId;
    private String name;
    private String email;
    private String phone;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

     public Integer getCustomerId() {
         return customerId;
     }

     public void setCustomerId(Integer customerId) {
         this.customerId = customerId;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
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

     public CustomerResponse() {
     }

     public CustomerResponse(Integer customerId, String name, String email, String phone) {
         this.customerId = customerId;
         this.name = name;
         this.email = email;
         this.phone = phone;
     }
 }

