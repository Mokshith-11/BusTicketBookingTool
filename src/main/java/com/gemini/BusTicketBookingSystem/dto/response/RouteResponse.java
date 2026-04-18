package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Builder

public class RouteResponse {


    private Integer routeId;
    private String fromCity;
    private String toCity;
    private Integer breakPoints;
    private Integer duration;


    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

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
}

     public Integer getRouteId() {
         return routeId;
     }

     public void setRouteId(Integer routeId) {
         this.routeId = routeId;
     }

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

     public RouteResponse() {
     }

     public RouteResponse(Integer routeId, String fromCity, String toCity, Integer breakPoints, Integer duration) {
         this.routeId = routeId;
         this.fromCity = fromCity;
         this.toCity = toCity;
         this.breakPoints = breakPoints;
         this.duration = duration;
     }
 }


