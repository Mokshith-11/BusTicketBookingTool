package com.gemini.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "routes")
 @Builder
/*
 * Beginner guide:
 * - This entity represents the Route table/object stored in PostgreSQL.
 * - JPA annotations such as @Entity, @Id, @Column, @ManyToOne, and @OneToMany explain how Java fields map to database columns and relationships.
 * - Repositories save and read this entity; services convert it to DTOs before sending data back to the frontend.
 */
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @NotBlank(message = "From city is required")
    @Column(name = "from_city", nullable = false)
    private String fromCity;

    @NotBlank(message = "To city is required")
    @Column(name = "to_city", nullable = false)
    private String toCity;

    @Column(name = "break_points")
    private Integer breakPoints;

    @Column(name = "duration")
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

    public Route() {
    }

    public Route(Integer routeId, String fromCity, String toCity, Integer breakPoints, Integer duration) {
        this.routeId = routeId;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.breakPoints = breakPoints;
        this.duration = duration;
    }
}