package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.RouteRequest;
import com.gemini.BusTicketBookingSystem.dto.response.RouteResponse;

import java.util.List;
/*
 * Beginner guide:
 * - This service interface lists the Route actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface IRouteService {
    RouteResponse createRoute(RouteRequest requestDTO);
    List<RouteResponse> getAllRoutes();
    RouteResponse getRouteById(Integer routeId);
    RouteResponse updateRoute(Integer routeId, RouteRequest requestDTO);
    void disableRoute(Integer routeId);
}