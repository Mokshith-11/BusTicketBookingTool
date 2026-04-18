package com.gemini.BusTicketBookingSystem.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

 @Builder
public class CustomerRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Address ID is required")
    private Integer addressId;

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

     public Integer getAddressId() {
         return addressId;
     }

     public void setAddressId(Integer addressId) {
         this.addressId = addressId;
     }

     public CustomerRequest() {
     }

     public CustomerRequest(String name, String email, String phone, Integer addressId) {
         this.name = name;
         this.email = email;
         this.phone = phone;
         this.addressId = addressId;
     }
 }
