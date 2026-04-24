package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.RouteRequest;
import com.gemini.BusTicketBookingSystem.dto.response.RouteResponse;

import java.util.List;
/*
 * - This service interface lists the Route actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

/**
 * Service interface for route management.
 * Defines the contract for creating, viewing, updating, and disabling bus routes.
 * Implemented by RouteServiceImpl.
 */
public interface IRouteService {
    /** Creates a new route from one city to another */
    RouteResponse createRoute(RouteRequest requestDTO);

    /** Retrieves all routes from the database */
    List<RouteResponse> getAllRoutes();

    /** Retrieves a route by its unique ID */
    RouteResponse getRouteById(Integer routeId);

    /** Updates an existing route with new data */
    RouteResponse updateRoute(Integer routeId, RouteRequest requestDTO);

    /** Disables (deletes) a route from the system */
    void disableRoute(Integer routeId);
}