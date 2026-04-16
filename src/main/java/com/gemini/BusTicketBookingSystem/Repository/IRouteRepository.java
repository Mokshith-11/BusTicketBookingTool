package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.Route;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRouteRepository extends JpaRepository<Route,Integer> {
    @Query("SELECT r FROM Route r WHERE r.fromCity = :fromCity AND r.toCity = :toCity")
    List<Route> findByFromCityAndToCity(@Param("fromCity") String fromCity,
                                        @Param("toCity") String toCity);}
