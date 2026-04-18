package com.gemini.BusTicketBookingSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

 @Builder
public class RouteRequest {

    @NotBlank(message = "From city is required")
    private String fromCity;

    @NotBlank(message = "To city is required")
    private String toCity;

    private Integer breakPoints;
    private Integer duration;

     public String getFromCity() {
         return fromCity;
     }

     public void setFromCity(String fromCity) {
         this.fromCity = fromCity;
     }

     public String getToCity() {
         return toCity;
     }

     public void setToCity(String toCity) {
         this.toCity = toCity;
     }

     public Integer getBreakPoints() {
         return breakPoints;
     }

     public void setBreakPoints(Integer breakPoints) {
         this.breakPoints = breakPoints;
     }

     public Integer getDuration() {
         return duration;
     }

     public void setDuration(Integer duration) {
         this.duration = duration;
     }

     public RouteRequest() {
     }

     public RouteRequest(String fromCity, String toCity, Integer breakPoints, Integer duration) {
         this.fromCity = fromCity;
         this.toCity = toCity;
         this.breakPoints = breakPoints;
         this.duration = duration;
     }
 }