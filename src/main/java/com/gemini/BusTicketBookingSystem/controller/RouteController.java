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

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    @Autowired
    private IRouteService routeService;

    // ✅ CREATE ROUTE
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

    // ✅ GET ALL ROUTES
    @GetMapping
    public ResponseEntity<ApiResponse<List<RouteResponse>>> getAllRoutes() {

        List<RouteResponse> routes = routeService.getAllRoutes();

        ApiResponse<List<RouteResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Routes fetched successfully",
                        routes);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ GET ROUTE BY ID
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

    // ✅ UPDATE ROUTE
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

    // ✅ DISABLE ROUTE (DELETE)
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