package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.RouteRequest;
import com.gemini.BusTicketBookingSystem.dto.response.RouteResponse;

import java.util.List;

public interface IRouteService {
    RouteResponse createRoute(RouteRequest requestDTO);
    List<RouteResponse> getAllRoutes();
    RouteResponse getRouteById(Integer routeId);
    RouteResponse updateRoute(Integer routeId, RouteRequest requestDTO);
    void disableRoute(Integer routeId);
}