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
/*
 * - This controller is the API entry point for Route requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class RouteController {

    @Autowired
    private IRouteService routeService;

    // ✅ CREATE ROUTE
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
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

    // ✅ GET ALL ROUTES
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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

    // ✅ GET ROUTE BY ID
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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

    // ✅ UPDATE ROUTE
    /*
     * PUT flow:
     * - URL gives the record ID and the body gives the new values.
     * - @Valid checks the body, then service finds the old record and updates it.
     * - If the ID does not exist, the service throws ResourceNotFoundException.
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

    // ✅ DISABLE ROUTE (DELETE)
    // DELETE disables or removes a resource by ID.
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