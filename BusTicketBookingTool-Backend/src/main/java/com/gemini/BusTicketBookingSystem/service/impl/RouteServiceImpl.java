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

/**
 * Service implementation for managing bus routes.
 * Contains business logic for creating, retrieving, updating,
 * and disabling routes. Ensures no duplicate routes exist and
 * validates that source and destination cities are different.
 */
@Service
/*
 * - This class contains the real business logic for Route operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */
public class RouteServiceImpl implements IRouteService {

    @Autowired
    private IRouteRepository routeRepository;

    /**
     * Creates a new bus route from one city to another.
     * Validates two business rules:
     * 1. From city and to city cannot be the same
     * 2. A route with the same fromCity-toCity combination must not already exist
     * Sets break points (number of stops) and duration for the route.
     * This method is transactional to ensure data consistency.
     *
     * @param requestDTO - contains fromCity, toCity, breakPoints, duration
     * @return RouteResponse - the created route data with generated ID
     */
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

    /**
     * Retrieves all routes stored in the database.
     * Maps each Route entity to a RouteResponse DTO.
     *
     * @return List of RouteResponse - all routes
     */
    @Override
    public List<RouteResponse> getAllRoutes() {
        return routeRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single route by its unique route ID.
     * Throws ResourceNotFoundException if no route exists with that ID.
     *
     * @param routeId - the unique ID of the route
     * @return RouteResponse - the route details
     */
    @Override
    public RouteResponse getRouteById(Integer routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "routeId", routeId));
        return convertToResponseDTO(route);
    }

    /**
     * Updates an existing route's details.
     * Validates two business rules:
     * 1. From city and to city cannot be the same
     * 2. No other route with the same fromCity-toCity pair should exist
     *    (excludes the current route being updated from the duplicate check)
     * Updates fromCity, toCity, breakPoints, and duration.
     * This method is transactional to ensure data consistency.
     *
     * @param routeId    - the ID of the route to update
     * @param requestDTO - the new route data
     * @return RouteResponse - the updated route data
     */
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

    /**
     * Disables (deletes) a route from the database.
     * Finds the route by ID and permanently removes it.
     * Throws ResourceNotFoundException if the route doesn't exist.
     * This method is transactional to ensure data consistency.
     *
     * @param routeId - the ID of the route to disable
     */
    @Override
    @Transactional
    public void disableRoute(Integer routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "routeId", routeId));


        routeRepository.delete(route);
    }

    /**
     * Helper method to convert a Route entity to a RouteResponse DTO.
     * Maps route ID, fromCity, toCity, breakPoints, and duration.
     *
     * @param route - the Route entity to convert
     * @return RouteResponse - the mapped DTO
     */
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
