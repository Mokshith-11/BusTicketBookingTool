package com.gemini.BusTicketBookingSystem.Service;


import com.gemini.BusTicketBookingSystem.Dto.Request.RouteRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.RouteResponse;

import java.util.List;

public interface IRouteService {
    RouteResponse createRoute(RouteRequest requestDTO);
    List<RouteResponse> getAllRoutes();
    RouteResponse getRouteById(Integer routeId);
    RouteResponse updateRoute(Integer routeId, RouteRequest requestDTO);
    void disableRoute(Integer routeId);
}