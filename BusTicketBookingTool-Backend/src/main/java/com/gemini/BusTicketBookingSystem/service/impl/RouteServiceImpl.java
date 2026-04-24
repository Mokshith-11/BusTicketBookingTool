package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.Route;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.InvalidOperationException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IRouteRepository;
import com.gemini.BusTicketBookingSystem.service.IRouteService;
import com.gemini.BusTicketBookingSystem.dto.request.RouteRequest;
import com.gemini.BusTicketBookingSystem.dto.response.RouteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
/*
 * Beginner guide:
 * - This class contains the real business logic for Route operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */
public class RouteServiceImpl implements IRouteService {

    @Autowired
    private IRouteRepository routeRepository;

    @Override
    @Transactional
    public RouteResponse createRoute(RouteRequest requestDTO) {
        if (requestDTO.getFromCity().equalsIgnoreCase(requestDTO.getToCity())) {
            throw new InvalidOperationException("Create Route",
                    "From city and To city cannot be the same");
        }

        List<Route> existingRoutes = routeRepository.findByFromCityAndToCity(
                requestDTO.getFromCity(), requestDTO.getToCity());

        if (!existingRoutes.isEmpty()) {
            throw new DuplicateResourceException("Route",
                    "fromCity-toCity",
                    requestDTO.getFromCity() + " to " + requestDTO.getToCity());
        }

        Route route = new Route();
        route.setFromCity(requestDTO.getFromCity());
        route.setToCity(requestDTO.getToCity());
        route.setBreakPoints(requestDTO.getBreakPoints());
        route.setDuration(requestDTO.getDuration());

        Route savedRoute = routeRepository.save(route);
        return convertToResponseDTO(savedRoute);
    }

    @Override
    public List<RouteResponse> getAllRoutes() {
        return routeRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RouteResponse getRouteById(Integer routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "routeId", routeId));
        return convertToResponseDTO(route);
    }

    @Override
    @Transactional
    public RouteResponse updateRoute(Integer routeId, RouteRequest requestDTO) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "routeId", routeId));

        if (requestDTO.getFromCity().equalsIgnoreCase(requestDTO.getToCity())) {
            throw new InvalidOperationException("Update Route",
                    "From city and To city cannot be the same");
        }

        List<Route> existingRoutes = routeRepository.findByFromCityAndToCity(
                requestDTO.getFromCity(), requestDTO.getToCity());

        boolean duplicateExists = existingRoutes.stream()
                .anyMatch(r -> !r.getRouteId().equals(routeId));

        if (duplicateExists) {
            throw new DuplicateResourceException("Route",
                    "fromCity-toCity",
                    requestDTO.getFromCity() + " to " + requestDTO.getToCity());
        }

        route.setFromCity(requestDTO.getFromCity());
        route.setToCity(requestDTO.getToCity());
        route.setBreakPoints(requestDTO.getBreakPoints());
        route.setDuration(requestDTO.getDuration());

        Route updatedRoute = routeRepository.save(route);
        return convertToResponseDTO(updatedRoute);
    }

    @Override
    @Transactional
    public void disableRoute(Integer routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "routeId", routeId));


        routeRepository.delete(route);
    }

    private RouteResponse convertToResponseDTO(Route route) {
        RouteResponse dto = new RouteResponse();
        dto.setRouteId(route.getRouteId());
        dto.setFromCity(route.getFromCity());
        dto.setToCity(route.getToCity());
        dto.setBreakPoints(route.getBreakPoints());
        dto.setDuration(route.getDuration());
        return dto;
    }
}

