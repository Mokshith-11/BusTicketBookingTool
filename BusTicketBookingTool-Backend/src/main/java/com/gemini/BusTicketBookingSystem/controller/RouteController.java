package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.RouteRequest;
import com.gemini.BusTicketBookingSystem.dto.response.RouteResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IRouteService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing bus routes.
 * Handles creating, viewing, updating, and disabling routes.
 * A route defines the path from one city to another with break points and duration.
 * Base URL: /api/v1/routes
 */
@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    @Autowired
    private IRouteService routeService;

    /**
     * Creates a new bus route.
     * Validates that the source and destination cities are different,
     * and checks that no duplicate route (same fromCity-toCity pair) already exists.
     *
     * @param requestDTO - route details: fromCity, toCity, breakPoints, duration (validated with @Valid)
     * @return ResponseEntity with HTTP 201 (Created) and the created route data
     */
    @PostMapping
    public ResponseEntity<ApiResponse<RouteResponse>> createRoute(
            @Valid @RequestBody RouteRequest requestDTO) {

        RouteResponse response = routeService.createRoute(requestDTO);

        ApiResponse<RouteResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Route created successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Retrieves a list of all available bus routes.
     * Returns every route stored in the database.
     *
     * @return ResponseEntity with HTTP 200 (OK) and a list of all routes
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<RouteResponse>>> getAllRoutes() {

        List<RouteResponse> routes = routeService.getAllRoutes();

        ApiResponse<List<RouteResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Routes fetched successfully",
                        routes);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a single route by its unique route ID.
     * If the route is not found, a ResourceNotFoundException is thrown.
     *
     * @param routeId - the unique identifier of the route to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and the route details
     */
    @GetMapping("/{routeId}")
    public ResponseEntity<ApiResponse<RouteResponse>> getRouteById(
            @PathVariable Integer routeId) {

        RouteResponse response = routeService.getRouteById(routeId);

        ApiResponse<RouteResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Route fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Updates an existing route's details.
     * Finds the route by ID and updates fromCity, toCity, breakPoints, and duration.
     * Validates that fromCity and toCity are different and no duplicate route exists.
     *
     * @param routeId    - the unique ID of the route to update
     * @param requestDTO - the new route details (validated with @Valid)
     * @return ResponseEntity with HTTP 200 (OK) and the updated route data
     */
    @PutMapping("/{routeId}")
    public ResponseEntity<ApiResponse<RouteResponse>> updateRoute(
            @PathVariable Integer routeId,
            @Valid @RequestBody RouteRequest requestDTO) {

        RouteResponse response = routeService.updateRoute(routeId, requestDTO);

        ApiResponse<RouteResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Route updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Disables (deletes) a route by its ID.
     * Permanently removes the route from the system.
     * Throws ResourceNotFoundException if the route doesn't exist.
     *
     * @param routeId - the unique ID of the route to disable
     * @return ResponseEntity with HTTP 200 (OK) and a success message
     */
    @DeleteMapping("/{routeId}")
    public ResponseEntity<ApiResponse<String>> disableRoute(
            @PathVariable Integer routeId) {

        routeService.disableRoute(routeId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Route disabled successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }
}