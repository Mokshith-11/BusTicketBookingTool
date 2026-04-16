package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.Route;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRouteRepository extends JpaRepository<Route,Integer> {
    List<Route> findByFromCityAndToCity(@NotBlank(message = "From city is required") String fromCity, @NotBlank(message = "To city is required") String toCity);
}
